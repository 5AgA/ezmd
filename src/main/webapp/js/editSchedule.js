document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById('editSchedule-modal');
    const categoryContainer = document.querySelector(".edit-category");

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

            // 카테고리 버튼 클릭 이벤트 추가
            const categoryButtons = document.querySelectorAll(".edit-category-btn");
            categoryButtons.forEach(button => {
                button.addEventListener("click", () => {
                    const categoryId = button.dataset.categoryId;

                    if (selectedCategory === categoryId) {
                        selectedCategory = '';
                        button.classList.remove('selected');
                        button.style.border = ''; // 회색 테두리 제거
                    } else {
                        if (selectedCategory !== '') {
                            const prevButton = document.querySelector(`[data-category-id='${selectedCategory}']`);
                            prevButton.classList.remove('selected');
                            prevButton.style.border = ''; // 이전 카테고리의 회색 테두리 제거
                        }

                        selectedCategory = categoryId;
                        button.classList.add('selected');
                        button.style.border = '2px solid gray'; // 회색 테두리 추가
                    }
                });
            });
        })
        .catch(error => console.error("Error fetching categories:", error));

    // schedule-item 클릭 시 모달 열기 (이벤트 위임)
    document.body.addEventListener('click', function (event) {
        // 클릭된 요소가 schedule-item 클래스인지 확인
        if (event.target.classList.contains('schedule-item')) {
            const scheduleId = event.target.dataset.sid;
            openEditScheduleModal(scheduleId); // scheduleId를 이용해 모달 열기
        }
    });

    // 일정 수정 모달 열기
    function openEditScheduleModal(scheduleId) {
        modal.style.display = 'flex';

        // Ajax 요청을 통해 스케줄 데이터 로드
        fetch(`/schedule/info?id=${scheduleId}`)
            .then(response => response.json())
            .then(schedule => {

                document.getElementById('edit-title').value = schedule.scheduleTitle;
                document.getElementById('edit-sdate').value = schedule.scheduleStart;
                document.getElementById('edit-edate').value = schedule.scheduleEnd;
                document.getElementById('edit-place').value = schedule.schedulePlace;
                document.getElementById('edit-memo').value = schedule.scheduleMemo;

                // 카테고리 버튼에서 선택된 카테고리 ID를 찾아 selected 클래스 추가
                const selectedCategoryButton = document.querySelector(`.edit-category-btn[data-category-id="${schedule.categoryId}"]`);
                if (selectedCategoryButton) {
                    selectedCategoryButton.classList.add('selected');
                }
            })
            .catch(error => console.error('Error loading schedule data:', error));
    }

    // 모달 닫기
    function closeModal() {
        modal.style.display = 'none';
    }

    // 모달 외부 클릭 시 모달 닫기
    window.onclick = function (event) {
        if (event.target === modal) {
            closeModal();
        }
    }

    // 일정 수정 폼 제출
    document.getElementById('editSchedule-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const startDate = new Date(document.getElementById('edit-sdate').value);
        const endDate = new Date(document.getElementById('edit-edate').value);

        if (startDate > endDate) {
            const errorElement = document.createElement('p');
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

        const formData = new FormData(this);
        fetch('/schedule/update', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('일정이 성공적으로 수정되었습니다.');
                    closeModal();
                } else {
                    alert('일정 수정에 실패했습니다.');
                }
            })
            .catch(error => console.error('Error updating schedule:', error));
    });
});
