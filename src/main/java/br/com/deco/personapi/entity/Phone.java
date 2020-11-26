package br.com.deco.personapi.entity;

import br.com.deco.personapi.enums.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JoinColumn(nullable = false)
    private PhoneType type;

    @JoinColumn(nullable = false)
    private String number;
}
