document.addEventListener("DOMContentLoaded", () => {
    const emodal = document.getElementById('editSchedule-modal');
    const categoryContainer = document.querySelector(".edit-category");
    let selectedCategory = ''; // 카테고리 ID
    let sId = ''; // 수정할 일정 ID

    // 카테고리 데이터 가져오기
    fetch("/schedule/categories")
        .then(response => response.json())
        .then(categories => {
            categories.forEach(category => {
                const button = document.createElement("input");
                button.type = "button";
                button.className = "edit-category-btn";
                button.dataset.categoryId = category.categoryId;
                button.value = category.categoryName;
                button.style.backgroundColor = category.categoryColor;
                categoryContainer.appendChild(button);
            });
        })
        .catch(error => console.error("Error fetching categories:", error));

    // 카테고리 버튼 클릭 시 선택 효과
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('edit-category-btn')) {
            const button = event.target;
            const categoryId = button.dataset.categoryId;

            // 기존에 선택된 버튼이 있으면, 그 버튼의 선택을 해제
            if (selectedCategory && selectedCategory !== categoryId) {
                const prevButton = document.querySelector('.edit-category-btn.selected');
                if (prevButton) {
                    prevButton.classList.remove('selected');
                    prevButton.style.border = ''; // 회색 테두리 제거
                }
            }

            // 카테고리 선택 상태 변경
            if (selectedCategory === categoryId) {
                // 카테고리 선택 해제
                selectedCategory = '';
                button.classList.remove('selected');
                button.style.border = ''; // 회색 테두리 제거
            } else {
                // 카테고리 선택
                selectedCategory = categoryId;
                button.classList.add('selected');
                button.style.border = '2px solid gray';
            }
        }
    });

    // schedule-item 클릭 시 모달 열기 (이벤트 위임)
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('schedule-item')) {
            const scheduleId = event.target.dataset.sid;
            openEditScheduleModal(scheduleId); // scheduleId를 이용해 모달 열기
        }
    });

    // 일정 수정 모달 열기
    function openEditScheduleModal(scheduleId) {
        emodal.style.display = 'flex';

        // Ajax 요청을 통해 스케줄 데이터 로드
        fetch(`/schedule/info?id=${scheduleId}`)
            .then(response => response.json())
            .then(schedule => {
                document.getElementById('edit-title').value = schedule.scheduleTitle;
                document.getElementById('edit-sdate').value = schedule.scheduleStart;
                document.getElementById('edit-edate').value = schedule.scheduleEnd;
                document.getElementById('edit-place').value = schedule.schedulePlace ? schedule.schedulePlace : '';
                document.getElementById('edit-memo').value = schedule.scheduleMemo ? schedule.scheduleMemo : '';
                sId = scheduleId;

                // 카테고리 버튼에서 선택된 카테고리 ID를 찾아 selected 클래스 추가
                const selectedCategoryButton = document.querySelector(`.edit-category-btn[data-category-id="${schedule.categoryId}"]`);
                if (selectedCategoryButton) {
                    selectedCategoryButton.classList.add('selected');
                    selectedCategoryButton.style.border = '2px solid gray';
                    selectedCategory = schedule.categoryId.toString();  // 모달 열 때 카테고리 ID 설정
                }
            })
            .catch(error => console.error('Error loading schedule data:', error));
    }

    // 모달 닫기
    function closeModal() {
        emodal.style.display = 'none';

        // 폼 초기화
        document.getElementById('edit-title').value = '';
        document.getElementById('edit-sdate').value = '';
        document.getElementById('edit-edate').value = '';
        document.getElementById('edit-place').value = '';
        document.getElementById('edit-memo').value = '';

        // 카테고리 버튼의 선택 상태 초기화
        const categoryButtons = document.querySelectorAll('.edit-category-btn');
        categoryButtons.forEach(button => {
            button.classList.remove('selected');
            button.style.border = ''; // 회색 테두리 제거
        });

        selectedCategory = '';
        sId = '';
        if(document.querySelector('.error-message')) {
            document.querySelector('.error-message').remove(); // 에러 메시지 삭제
        }
        location.reload();
    }

    // 모달 외부 클릭 시 모달 닫기
    window.onclick = function (event) {
        if (event.target === emodal) {
            closeModal();
        }
    }

    // 삭제 버튼 클릭 시 일정 삭제
    document.getElementById('delete').addEventListener('click', function () {
        if (confirm('정말 이 일정을 삭제하시겠습니까?')) {
            fetch(`/schedule/delete?id=${sId}`, {
                method: 'DELETE',  // HTTP DELETE 메서드 사용
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status == "success") {
                        alert('일정이 삭제되었습니다.');
                        setTimeout(() => {
                            closeModal();  // 모달 닫기
                            handleClick({ target: document.querySelector('.selected') });
                        }, 0);
                        // 페이지 갱신 또는 일정 목록을 새로고침하는 로직 추가 가능
                    } else {
                        alert('일정 삭제에 실패했습니다.');
                        setTimeout(() => {
                            closeModal();  // 모달 닫기
                        }, 0);
                    }
                })
                .catch(error => console.error('Error deleting schedule:', error));
        }
    });

    // 일정 수정 폼 제출
    document.getElementById('editSchedule-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const startDate = new Date(document.getElementById('edit-sdate').value);
        const endDate = new Date(document.getElementById('edit-edate').value);

        if (startDate > endDate) {
            const errorElement = document.createElement('p');
            errorElement.className = 'error-message';
            errorElement.textContent = '시작일은 종료일보다 앞서야 합니다.';
            errorElement.style.color = 'red';
            document.querySelector('.edit-date').appendChild(errorElement);
            document.getElementById('edit-sdate').focus();
            return;
        }

        if (selectedCategory === '') {
            const errorElement = document.createElement('p');
            errorElement.textContent = '카테고리를 선택해주세요.';
            errorElement.style.color = 'red';
            document.querySelector('.edit-category').appendChild(errorElement);
            return;
        }

        const scheduleId = sId; // 수정할 일정 ID
        const scheduleTitle = document.getElementById('edit-title').value;
        // 로컬 시간대 기준으로 포맷하기
        const scheduleStart = `${startDate.getFullYear()}-${(startDate.getMonth() + 1).toString().padStart(2, '0')}-${startDate.getDate().toString().padStart(2, '0')} ${startDate.getHours().toString().padStart(2, '0')}:${startDate.getMinutes().toString().padStart(2, '0')}`;
        const scheduleEnd = `${endDate.getFullYear()}-${(endDate.getMonth() + 1).toString().padStart(2, '0')}-${endDate.getDate().toString().padStart(2, '0')} ${endDate.getHours().toString().padStart(2, '0')}:${endDate.getMinutes().toString().padStart(2, '0')}`;

        const schedulePlace = document.getElementById('edit-place').value;
        const scheduleMemo = document.getElementById('edit-memo').value;
        const categoryId = selectedCategory; // 이미 선택된 카테고리 ID

        const scheduleData = {
            scheduleId: scheduleId,
            scheduleTitle: scheduleTitle,
            scheduleStart: scheduleStart,
            scheduleEnd: scheduleEnd,
            schedulePlace: schedulePlace,
            scheduleMemo: scheduleMemo,
            categoryId: categoryId
        };

        fetch('/schedule/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(scheduleData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert('일정이 성공적으로 수정되었습니다.');
                    setTimeout(() => {
                        closeModal();  // 모달 닫기
                    }, 0);
                } else {
                    alert('일정 수정에 실패했습니다.');
                    setTimeout(() => {
                        closeModal();  // 모달 닫기
                    }, 0);
                }
            })
            .catch(error => console.error('Error updating schedule:', error));
    });
});
