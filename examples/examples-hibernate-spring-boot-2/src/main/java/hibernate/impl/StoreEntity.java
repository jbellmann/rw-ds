package hibernate.impl;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
