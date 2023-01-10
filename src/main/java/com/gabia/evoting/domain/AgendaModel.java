package com.gabia.evoting.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


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
        LIMIT(0),
        UNLIMIT(1);

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

    @Column
    private LocalDateTime startDatetime;

    @Column
    private LocalDateTime endDatetime;

    @Column
    private int max_vote;

    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private Type type;

    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
    private List<VoteModel> voteModels;
}
