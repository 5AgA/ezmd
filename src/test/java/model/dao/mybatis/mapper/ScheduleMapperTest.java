package model.dao.mybatis.mapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.InputStream;

public class ScheduleMapperTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private ScheduleMapper scheduleMapper;
    private ScheduleCategoryMapper scheduleCategoryMapper;
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
        scheduleCategoryMapper = session.getMapper(ScheduleCategoryMapper.class);
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

//    @Test
//    public void testInsertScheduleCategory() {
//        // 테스트할 데이터 생성
//        ScheduleCategory category = new ScheduleCategory();
//        category.setCategoryName("알바");
//        category.setCategoryColor("#FF5733");
//        category.setUserId(userId);
//
//        // 카테고리 삽입
//        int result = scheduleCategoryMapper.insertScheduleCategory(category);
//
//        // 데이터가 성공적으로 삽입되었는지 검증
//        assertEquals(1, result); // 반환 값은 1이어야 합니다.
//    }

//    @Test
//    public void testSelectCategoriesByUserId() {
//        // 특정 userId로 카테고리 목록 조회
//        List<ScheduleCategory> categories = scheduleCategoryMapper.selectCategoriesByUserId(userId);
//
//        // 조회된 카테고리 리스트가 null이 아닌지 확인
//        assertNotNull(categories);
//        assertTrue(categories.size() > 0); // 적어도 하나 이상의 카테고리가 있어야 합니다.
//
//        // 카테고리 목록 출력
//        categories.forEach(category -> System.out.println(category));
//    }

//    @Test
//    public void testSelectScheduleCategoryById() {
//        // ID로 특정 카테고리 조회
//        ScheduleCategory category = scheduleCategoryMapper.selectScheduleCategoryById(1);
//
//        // 카테고리가 null이 아니어야 하고, 필드가 예상대로 설정되어 있어야 합니다.
//        assertNotNull(category);
//        assertEquals("Work", category.getCategoryName());
//        assertEquals("#FF5733", category.getCategoryColor());
//    }

//    @Test
//    public void testUpdateScheduleCategory() {
//        // 기존 카테고리 수정
//        ScheduleCategory category = new ScheduleCategory();
//        category.setCategoryId(4);
//        category.setCategoryName("일정");
//        category.setCategoryColor("#33FF22");
//        category.setUserId(userId);
//
//        // 카테고리 업데이트
//        int result = scheduleCategoryMapper.updateScheduleCategory(category);
//
//        // 업데이트가 성공적으로 이루어졌는지 확인
//        assertEquals(1, result);
//    }

//    @Test
//    public void testDeleteScheduleCategory() {
//        // 특정 카테고리 삭제
//        int result = scheduleCategoryMapper.deleteScheduleCategory(22);
//
//        // 삭제가 성공적으로 이루어졌는지 확인
//        assertEquals(1, result);
//    }

    // 종료 시 세션 닫기
    @AfterEach
    public void tearDown() {
        if (session != null) {
//            session.commit();
            session.close();
        }
    }
}
