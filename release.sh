#!/usr/bin/env bash

set -euxo pipefail

: "${1?"Usage: $0 <[pre]major|[pre]minor|[pre]patch|prerelease>"}"

./mvnw scm:check-local-modification

current=$({ echo 0.0.0; git tag --list --sort=version:refname; } | tail -n1)
release=$(semver "${current}" -i "$1" --preid RC)
next=$(semver "${release}" -i minor)

./mvnw versions:set -D newVersion="${release}" -P spring-boot-3,spring-boot-2,examples
git commit -am "Release ${release}"
./mvnw clean deploy scm:tag -P release,spring-boot-2 -D tag="${release}" -D pushChanges=false -D skipTests -D dependency-check.skip

./mvnw versions:set -D newVersion="${next}-SNAPSHOT" -P spring-boot-3,spring-boot-2,examples

git push --atomic origin main "${release}"
