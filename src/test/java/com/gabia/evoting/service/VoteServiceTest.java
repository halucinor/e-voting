package com.gabia.evoting.service;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.domain.VoteModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.repository.VoteRepository;
import com.gabia.evoting.web.dto.VoteRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoteServiceTest {
    @Autowired
    AgendaRepository agendaRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteService voteService;
    @Autowired
    static DataSource dataSource;
    @BeforeAll
    public static void init() {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("src/main/resources/data.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("제한이 있는 안건의 선착순 의결권 행사 테스트")
    void limited_vote() throws InterruptedException {
        /*
        * agenda(103)
        * Limit agenda
        * 최대 투표 수 100
        * */
        AgendaModel agendaModel = agendaRepository.findById(103L).orElseThrow(IllegalArgumentException::new);

        agendaModel.setEndDateTime(LocalDateTime.now().plusMinutes(5));

        ExecutorService service = Executors.newFixedThreadPool(10);

        CountDownLatch latch = new CountDownLatch(10);
        AtomicInteger successCount = new AtomicInteger();

        for(long i = 101L; i <= 110L ; i++){
            long id = i;
            VoteRequestDto requestDto = new VoteRequestDto();
            requestDto.setVoteCount(30L);
            requestDto.setAgendaId(103L);
            requestDto.setType(VoteModel.Type.AGREE);
            UserModel user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);

            service.execute(()-> {
                if(voteService.vote(user,requestDto).getVoteStatus() == "success"){
                    successCount.getAndIncrement();
                };
                latch.countDown();
            });
        }

        latch.await();

        assertThat(successCount.get()).isEqualTo(4);
    }
    @Test
    @DisplayName("제한이 없는 안건의 의결권 행사 테스트")
    void unlimited_vote() {
        /*
         * agenda(104)
         * UnLimit agenda
         *
         * 모든 사용자는 100개의 의결권을 가지고 있음
         * */
        Long agendaId = 104L;
        AgendaModel agendaModel = agendaRepository.findById(agendaId).orElseThrow(IllegalArgumentException::new);

        agendaModel.setEndDateTime(LocalDateTime.now().plusMinutes(5));

        for(long i = 101L; i <= 110L ; i++){
            long id = i;
            VoteRequestDto requestDto = new VoteRequestDto();
            requestDto.setVoteCount(100L);
            requestDto.setAgendaId(agendaId);
            requestDto.setType(VoteModel.Type.AGREE);
            UserModel user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            voteService.vote(user,requestDto);
        }

        Long totalVoteCount = voteService.getSumOfVote(agendaModel);
        assertThat(totalVoteCount).isEqualTo(1000L);
    }

}