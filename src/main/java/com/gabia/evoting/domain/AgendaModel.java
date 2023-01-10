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

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private int maxVote;

    @Column
    private Status status;
    @Column
    private Type type;

    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
    private List<VoteModel> voteModels;
}
