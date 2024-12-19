package model.dao.mybatis.mapper;

import model.domain.Schedule;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.List;

public class MapperTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private ScheduleMapper scheduleMapper;
    private int userId = 20210670;

    @BeforeEach
    public void setUp() throws Exception {
        // MyBatis 설정 파일 경로
        String resource = "mybatis-config.xml";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);

        // SqlSessionFactory 생성
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // SqlSession 생성
        session = sqlSessionFactory.openSession();

        // Mapper 가져오기
        scheduleMapper = session.getMapper(ScheduleMapper.class);
    }

//    @Test
//    public void testInsertSchedule() {
//        // 새로운 스케줄 데이터 생성
//        Schedule newSchedule = new Schedule();
//        newSchedule.setScheduleTitle("Test Schedule");
//        newSchedule.setScheduleStart(Schedule.parseDateTime("2024-12-19 10:00"));
//        newSchedule.setScheduleEnd(Schedule.parseDateTime("2024-12-19 12:00"));
//        newSchedule.setScheduleRepeat(0);
//        newSchedule.setSchedulePlace("Test Place");
//        newSchedule.setScheduleMemo("Test Memo");
//        newSchedule.setCategoryId(2);
//        newSchedule.setUserId(userId);
//
//        // 스케줄 삽입
//        int result = scheduleMapper.insertSchedule(newSchedule);
//        assertEquals(1, result, "스케줄 삽입에 실패했습니다.");
//        assertNotNull(newSchedule.getScheduleId(), "스케줄 ID가 null입니다.");
//    }

//    @Test
//    public void testGetSchedulesByUserId() {
//        // 사용자 ID로 스케줄 목록 가져오기
//        List<Schedule> schedules = scheduleMapper.getSchedulesByUserId(userId);
//        assertNotNull(schedules, "스케줄 목록이 null입니다.");
//        assertTrue(schedules.size() > 0, "사용자 ID에 해당하는 스케줄이 없습니다.");
//
//        // 첫 번째 스케줄 내용 확인
//        schedules.forEach(schedule -> System.out.println(schedule));
//    }

//    @Test
//    public void testUpdateSchedule() {
//        // 기존에 삽입된 스케줄 ID
//        int scheduleId = 66;  // 테스트용 스케줄 ID
//
//        // 스케줄 정보 수정
//        Schedule scheduleToUpdate = new Schedule();
//        scheduleToUpdate.setScheduleId(scheduleId);
//        scheduleToUpdate.setScheduleTitle("Updated Schedule");
//        scheduleToUpdate.setScheduleStart(Schedule.parseDateTime("2024-12-20 10:00"));
//        scheduleToUpdate.setScheduleEnd(Schedule.parseDateTime("2024-12-20 12:00"));
//        scheduleToUpdate.setScheduleRepeat(0);
//        scheduleToUpdate.setSchedulePlace("Updated Place");
//        scheduleToUpdate.setScheduleMemo("Updated Memo");
//        scheduleToUpdate.setCategoryId(2); // 카테고리 ID
//        scheduleToUpdate.setUserId(20210670);     // 사용자 ID
//
//        // 스케줄 업데이트
//        int result = scheduleMapper.updateSchedule(scheduleToUpdate);
//        assertEquals(1, result, "스케줄 업데이트에 실패했습니다.");
//    }

//    @Test
//    public void testDeleteSchedule() {
//        // 기존에 삽입된 스케줄 ID
//        int scheduleId = 66;  // 테스트용 스케줄 ID
//
//        // 스케줄 삭제
//        int result = scheduleMapper.deleteSchedule(scheduleId);
//        assertEquals(1, result, "스케줄 삭제에 실패했습니다.");
//    }

    // 종료 시 세션 닫기
    @AfterEach
    public void tearDown() {
        if (session != null) {
            session.commit();
            session.close();
        }
    }
}
