package com.gabia.evoting.schedule;

import com.gabia.evoting.service.AgendaService;
import com.gabia.evoting.web.dto.AgendaEndRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class TaskDefinitionBean implements Runnable {

    private AgendaEndRequestDto agendaEndRequestDto;
    private final AgendaService agendaService;

    @Override
    public void run() {
        System.out.println("Running Scheduled Task with Agenda Id : " + agendaEndRequestDto.getAgendaId());
        agendaService.endAgenda(agendaEndRequestDto.getAgendaId()); // end agenda
    }
}