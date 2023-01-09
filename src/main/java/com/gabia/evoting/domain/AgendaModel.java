package com.gabia.evoting.domain;

import com.gabia.evoting.domain.user.BaseUserModel;
import com.gabia.evoting.domain.user.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@Entity(name = "AGENDA")
public class AgendaModel {

    @Getter
    public enum Status {
        WAIT(0),
        START(1),
        END(2);

        private final int value;
        Status(int value) {
            this.value = value;
        }
    }

    @Getter
    public enum Type {
        Limit(0),
        UnLimit(1);

        private final int value;

        Type(int value){
            this.value = value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column(nullable = false)
    private int max_vote;

    @Column
    private Status status;
    @Column
    private Type type;
}
