package com.gabia.evoting.service;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.web.dto.AgendaRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//https://github.com/lattechiffon/junit-study/tree/f8b93bb8030b9572e391230172493c83e17e7a34
@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;
    @InjectMocks
    private AgendaService agendaservice;

//    @BeforeAll

    @Test
    @DisplayName("Agenda 저장 테스트")
    void agenda_save() {

        AgendaModel agenda = AgendaModel.builder()
                .description("test")
                .maxVote(10000)
                .status(AgendaModel.Status.START)
                .type(AgendaModel.Type.LIMIT)
                .build();

        when(agendaRepository.save(any())).thenReturn(agenda);

        AgendaModel result = agendaRepository.save(agenda);

        verify(agendaRepository, times(1)).save(any());
        assertThat(agendaRepository.save(agenda), equalTo(agenda));
    }

    @Test
    @DisplayName("Agenda 리스트 조회 테스트")
    void agenda_list() {
        AgendaModel agenda1 = AgendaModel.builder()
                .description("test")
                .maxVote(10000)
                .status(AgendaModel.Status.START)
                .type(AgendaModel.Type.LIMIT)
                .build();

        AgendaModel agenda2 = AgendaModel.builder()
                .description("test2")
                .maxVote(10000)
                .status(AgendaModel.Status.START)
                .type(AgendaModel.Type.LIMIT)
                .build();


        AgendaModel agenda3 = AgendaModel.builder()
                .description("test2")
                .maxVote(10000)
                .status(AgendaModel.Status.START)
                .type(AgendaModel.Type.LIMIT)
                .build();

        List<AgendaModel> agendaModelList = new ArrayList<>();

        agendaRepository.save(agenda1);
        agendaRepository.save(agenda2);
        agendaRepository.save(agenda3);

        agendaModelList.add(agenda1);
        agendaModelList.add(agenda2);
        agendaModelList.add(agenda3);

        when(agendaRepository.findAll()).thenReturn(agendaModelList);

        List<AgendaModel> agendaList = agendaRepository.findAll();

        assertThat(agendaModelList, equalTo(agendaList));
    }



}