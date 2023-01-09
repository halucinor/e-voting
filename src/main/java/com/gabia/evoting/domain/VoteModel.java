package com.gabia.evoting.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "VOTE")
public class VoteModel {

    @Getter
    public enum Type{
        DISAGREE(0),
        AGREE(1),
        ABSSTENTION(2);

        private int value;
        Type(int i) {
            this.value = value;
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Type type;

    @Column
    private int count;

    @Column
    private LocalDateTime votingDateTime;
}

