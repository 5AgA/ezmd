const currentMonthElement = document.getElementById('currentMonth');
const calendarGrid = document.querySelector('.calendar-grid');

let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth(); // 0: 1월, 1: 2월...

// 월 이름 배열
const monthNames = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

// Fetch schedules from the server
async function fetchSchedules(userId) {
    const response = await fetch(`/schedule?userId=${userId}`);
    if (!response.ok) {
        throw new Error('서버에서 스케줄을 가져오는데 실패했습니다.');
    }
    return await response.json();
}

// 월간 달력을 표시하는 함수
function renderCalendar(year, month, userId) {
    const firstDay = new Date(year, month, 1).getDay(); // 해당 월의 첫 번째 요일
    const lastDate = new Date(year, month + 1, 0).getDate(); // 해당 월의 마지막 날짜
    const prevLastDate = new Date(year, month, 0).getDate(); // 이전 달의 마지막 날짜

    // 현재 날짜
    const today = new Date();
    const todayYear = today.getFullYear();
    const todayMonth = today.getMonth();
    const todayDate = today.getDate();

    // 제목 업데이트
    currentMonthElement.textContent = `${year}년 ${monthNames[month]}`;

    // calendar-grid에 직접 day-cell 추가
    calendarGrid.innerHTML = ''; // 기존 내용 초기화
    const dayNames = ['일', '월', '화', '수', '목', '금', '토'];

    // 요일 표시
    for (let day of dayNames) {
        const dayNameElement = document.createElement('div');
        dayNameElement.classList.add('day-name');
        dayNameElement.textContent = day;
        calendarGrid.appendChild(dayNameElement);
    }

    // 이전 달, 현재 달, 다음 달의 날짜를 순차적으로 추가
    for (let i = 1; i <= 35; i++) {
        const dayElement = document.createElement('div');
        dayElement.classList.add('day-cell');

        // 날짜 계산 및 클래스 추가
        if (i <= firstDay) {
            // 이전 달
            dayElement.textContent = prevLastDate - firstDay + i;
            dayElement.classList.add('prev-month');
        } else if (i <= firstDay + lastDate) {
            // 현재 달
            dayElement.textContent = i - firstDay;
            if (year === todayYear && month === todayMonth && i - firstDay === todayDate) {
                dayElement.classList.add('today');
            }

            // Add schedules to the day cell
            const daySchedules = schedules.filter(schedule => {
                const scheduleDate = new Date(schedule.scheduleStart);
                return scheduleDate.getFullYear() === year && scheduleDate.getMonth() === month && scheduleDate.getDate() === date;
            });

            daySchedules.forEach(schedule => {
                const scheduleElement = document.createElement('div');
                scheduleElement.classList.add('schedule');
                scheduleElement.textContent = schedule.scheduleTitle;
                dayElement.appendChild(scheduleElement);
            });
        } else {
            // 다음 달
            dayElement.textContent = i - firstDay - lastDate;
            dayElement.classList.add('next-month');
        }

        calendarGrid.appendChild(dayElement);
    }
}

// 이전 달 버튼
document.querySelectorAll('.month-nav')[0].addEventListener('click', () => {
    currentMonth--;
    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    }
    renderCalendar(currentYear, currentMonth, userId);
});

// 다음 달 버튼
document.querySelectorAll('.month-nav')[1].addEventListener('click', () => {
    currentMonth++;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    renderCalendar(currentYear, currentMonth, userId);
});


// 초기 달력 표시
const userId = 1;
renderCalendar(currentYear, currentMonth, userId);
