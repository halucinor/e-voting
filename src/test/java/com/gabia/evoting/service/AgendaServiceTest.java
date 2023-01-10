package com.gabia.evoting.service;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.web.dto.AgendaResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
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

        AgendaModel agenda = new AgendaModel();
        agenda.setId(1L);
        agenda.setType(AgendaModel.Type.LIMIT);
        agenda.setDescription("test");
        agenda.setStatus(AgendaModel.Status.START);
        agenda.setMax_vote(1000);
        agenda.setStartDatetime(LocalDateTime.now());
        agenda.setEndDatetime(LocalDateTime.now());

        when(agendaRepository.save(any())).thenReturn(agenda);

        AgendaModel result = agendaRepository.save(agenda);

        assertThat(result, equalTo(agenda));
    }

    @Test
    void agenda_list() {

    }

}