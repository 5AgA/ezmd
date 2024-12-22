const currentMonthElement = document.getElementById('currentMonth');
const calendarGrid = document.querySelector('.calendar-grid');

let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth(); // 0: 1월, 1: 2월...

// 월 이름 배열
const monthNames = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

let categories = []; // 카테고리 정보를 저장할 배열
// 카테고리 데이터를 서버에서 가져옵니다.
fetch('/schedule/categories?userId=20210670')
    .then(response => response.json())
    .then(data => {
        categories = data; // 카테고리 정보를 배열로 저장
    })
    .catch(error => console.error('Error fetching categories:', error));

// 날짜에 해당하는 스케줄을 가져오는 함수
function getScheduleForDate(date) {
    const todayInfo = document.querySelector('.today-info');

    const url = `/schedule/view?date=${date}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // 일정 정보 표시
            if (data.length > 0) {
                todayInfo.innerHTML = data.map(schedule => {
                    // schedule.categoryId로 해당 카테고리 정보 찾기
                    const category = categories.find(cat => cat.categoryId === schedule.categoryId);

                    return `
                        <div class="schedule-item" data-sid="${schedule.scheduleId}">
                            <h3>${schedule.scheduleTitle}</h3>
                            <p id="schedule-date">${schedule.scheduleStart} ~ ${schedule.scheduleEnd}</p>
                            <div class="schedule-place">
                                <img src="../images/place-icon.svg"><p>${schedule.schedulePlace}</p>
                            </div>
                            <input type="button" class="category-btn" data-category-id="${schedule.categoryId}" 
                            value="${category.categoryName}" style="background-color: ${category ? category.categoryColor : ''};">
                        </div>
                    `;
                }).join('');
            } else {
                todayInfo.innerHTML = "<p class=\"no-schedule\">해당 날짜에는 일정이 없습니다.</p>";
            }
        })
        .catch(error => {
            todayInfo.innerHTML = "<p class=\"no-schedule\">일정을 불러오는데 실패했습니다.</p>";
            console.error(error);
        });
}


// 날짜를 클릭했을 때 실행될 함수
function handleClick(event) {
    const clickedElement = event.target.closest('.day-cell');

    // 클릭된 요소가 prev-month나 next-month 클래스가 있으면 선택되지 않도록
    if (clickedElement.classList.contains('prev-month') || clickedElement.classList.contains('next-month')) {
        return; // prev-month나 next-month일 경우 아무 작업도 하지 않음
    }

    const days = document.querySelectorAll('.calendar-grid .day-cell');
    days.forEach(day => {
        day.classList.remove('selected');
    });

    // 현재 페이지가 /home이면 /schedule로 이동
    if (window.location.pathname === '/home') {
        window.location.href = '/schedule'; // /schedule로 리다이렉트
    } else {
        const clickedElement = event.target.closest('.day-cell');
        clickedElement.classList.toggle('selected');

        const selectedDate = clickedElement.querySelector('p').textContent;
        const today = new Date();
        const selectedDateObj = new Date(today.getFullYear(), today.getMonth(), selectedDate); // 선택한 날짜의 Date 객체 생성

        // 로컬 시간대에서 날짜를 변환
        const formattedDate
            = `${selectedDateObj.getFullYear()}-${(selectedDateObj.getMonth() + 1).toString().padStart(2, '0')}-${selectedDateObj.getDate().toString().padStart(2, '0')}`;

        // 선택된 날짜에 맞는 스케줄을 가져오는 함수 호출
        getScheduleForDate(formattedDate);
    }
}

// 월간 달력을 표시하는 함수
function renderCalendar(year, month) {
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

        // p 태그 추가
        const pElement = document.createElement('p');

        // 날짜 계산 및 클래스 추가
        let categoryColor = '';

        // 날짜 계산 및 클래스 추가
        if (i <= firstDay) {
            // 이전 달
            pElement.textContent = prevLastDate - firstDay + i;
            dayElement.classList.add('prev-month');
        } else if (i <= firstDay + lastDate) {
            // 현재 달
            const day = i - firstDay;
            pElement.textContent = i - firstDay;
            if (year === todayYear && month === todayMonth && i - firstDay === todayDate) {
                dayElement.classList.add('today');
            }
        } else {
            // 다음 달
            pElement.textContent = i - firstDay - lastDate;
            dayElement.classList.add('next-month');
        }

        // 토요일과 일요일에 id="weekend" 추가
        if ((i - firstDay) % 7 === 0 || (i - firstDay) % 7 === 1) { // 일요일(0) 또는 토요일(6)
            dayElement.id = 'weekend';
        }

        dayElement.addEventListener('click', handleClick);
        dayElement.appendChild(pElement);
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
    renderCalendar(currentYear, currentMonth);
});

// 다음 달 버튼
document.querySelectorAll('.month-nav')[1].addEventListener('click', () => {
    currentMonth++;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    renderCalendar(currentYear, currentMonth);
});

// 오늘 버튼
document.querySelector('.today-btn').addEventListener('click', () => {
    renderCalendar(new Date().getFullYear(), new Date().getMonth());
    const todayElement = document.querySelector('.today');

    // 클릭 이벤트 객체 생성
    const clickEvent = new MouseEvent('click', {
        bubbles: true, // 이벤트가 버블링되도록 설정
        cancelable: true, // 이벤트가 취소 가능한지 설정
    });

    // today 클래스를 가진 요소에 클릭 이벤트 디스패치
    todayElement.dispatchEvent(clickEvent);
});

// 초기 달력 표시
renderCalendar(currentYear, currentMonth);
