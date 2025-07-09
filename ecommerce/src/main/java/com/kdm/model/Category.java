package com.kdm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String name;

        @NotNull
        @Column(unique = true)
        private String categoryId;

        @ManyToOne
        private com.kdm.model.Category parentCategory;

        @NotNull
        private Integer level;
}
