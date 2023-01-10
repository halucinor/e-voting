package com.gabia.evoting.service;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.repository.AgendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//https://github.com/lattechiffon/junit-study/tree/f8b93bb8030b9572e391230172493c83e17e7a34
@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;
    @InjectMocks
    private AgendaService agendaservice;


    @Test
    void agenda_save() {

        AgendaModel agenda = AgendaModel.builder()
                .description("test")
                .maxVote(10000)
                .status(AgendaModel.Status.START)
                .type(AgendaModel.Type.LIMIT)
                .build();

        when(agendaRepository.save(any())).thenReturn(agenda);

        AgendaModel result = agendaRepository.save(agenda);

        assertThat(result, equalTo(agenda));
    }

    @Test
    void agenda_list() {

    }

}