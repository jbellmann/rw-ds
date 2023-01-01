package hibernate.impl;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String projectId;

  private String storeId;

  private String slug;

  private String state;

  private String uri;

  private LocalDateTime requestedAt;

}
