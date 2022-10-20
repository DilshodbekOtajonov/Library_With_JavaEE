package com.company.library.domains;

import com.company.library.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 5:23 PM (Saturday)
 * lib16/IntelliJ IDEA
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String name;
    private String surname;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.USER;
}
