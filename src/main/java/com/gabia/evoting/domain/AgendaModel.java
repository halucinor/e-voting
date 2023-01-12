package com.gabia.evoting.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
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
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;

    @Column
    private int maxVote;

    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private Type type;

    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
    private List<VoteModel> voteModels;

    @Builder
    public AgendaModel(String description, LocalDateTime startDateTime, LocalDateTime endDateTime, int maxVote, Status status,Type type){
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.maxVote = maxVote;
        this.status = status;
        this.type = type;
    }
}
