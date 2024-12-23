document.addEventListener("DOMContentLoaded", () => {
    const smodal = document.querySelector(".schedule-modal");
    const smodalBtn = document.getElementById("schedule-modal-btn");
    const actionButtons = document.querySelector(".action-buttons");
    let selectedCategory = ''; // 선택된 카테고리를 저장할 변수

    // 스케줄 모달 열기
    smodalBtn.addEventListener("click", () => {
        smodal.style.display = "flex";
        actionButtons.style.display = "none";
        document.getElementById("title").focus();
    });

    // 카테고리 데이터 가져오기
    const categoryContainer = document.querySelector(".category");
    fetch("/schedule/categories")
        .then(response => response.json())
        .then(categories => {
            categories.forEach(category => {
                const button = document.createElement("input");
                button.type = "button";
                button.className = "category-btn";
                button.dataset.categoryId = category.categoryId;
                button.value = category.categoryName;
                button.style.backgroundColor = category.categoryColor;
                categoryContainer.appendChild(button);
            });

            // 카테고리 버튼 클릭 이벤트 추가
            const categoryButtons = document.querySelectorAll(".category-btn");
            categoryButtons.forEach(button => {
                button.addEventListener("click", () => {
                    const categoryId = button.dataset.categoryId;

                    if (selectedCategory === categoryId) {
                        // 이미 선택된 카테고리를 다시 클릭하면 선택 해제
                        selectedCategory = '';
                        button.classList.remove('selected');
                        button.style.border = ''; // 회색 테두리 제거
                    } else {
                        // 선택된 카테고리를 업데이트
                        if (selectedCategory !== '') {
                            // 이미 다른 카테고리가 선택되어 있으면 그 카테고리에서 선택 해제
                            const prevButton = document.querySelector(`[data-category-id='${selectedCategory}']`);
                            prevButton.classList.remove('selected');
                            prevButton.style.border = ''; // 이전 카테고리의 회색 테두리 제거
                        }

                        // 새로운 카테고리를 선택
                        selectedCategory = categoryId;
                        button.classList.add('selected');
                        button.style.border = '2px solid gray'; // 회색 테두리 추가
                    }
                });
            });
        })
        .catch(error => console.error("Error fetching categories:", error));

    document.getElementById('schedule-form').addEventListener('submit', (event) => {
        event.preventDefault(); // 기본 submit 동작 방지

        const startDate = new Date(document.getElementById('sdate').value);
        const endDate = new Date(document.getElementById('edate').value);

        if (startDate > endDate) {
            // 경고 메시지 출력
            const errorElement = document.createElement('p');
            errorElement.className = 'error-message';
            errorElement.textContent = '시작일은 종료일보다 앞서야 합니다.';
            errorElement.style.color = 'red';
            document.querySelector('.date').appendChild(errorElement);

            // 입력 필드 포커스
            document.getElementById('sdate').focus();
            return;
        }

        // 카테고리가 선택되었는지 확인
        if (selectedCategory === '') {
            // 카테고리가 선택되지 않은 경우 경고 메시지 출력
            const errorElement = document.createElement('p');
            errorElement.className = 'error-message';
            errorElement.textContent = '카테고리를 선택해주세요.';
            errorElement.style.color = 'red';
            document.querySelector('.category').appendChild(errorElement);
            return;
        }

        const scheduleTitle = document.getElementById('title').value;
        const scheduleStart = `${startDate.getFullYear()}-${(startDate.getMonth() + 1).toString().padStart(2, '0')}-${startDate.getDate().toString().padStart(2, '0')} ${startDate.getHours().toString().padStart(2, '0')}:${startDate.getMinutes().toString().padStart(2, '0')}`;
        const scheduleEnd = `${endDate.getFullYear()}-${(endDate.getMonth() + 1).toString().padStart(2, '0')}-${endDate.getDate().toString().padStart(2, '0')} ${endDate.getHours().toString().padStart(2, '0')}:${endDate.getMinutes().toString().padStart(2, '0')}`;
        const schedulePlace = document.getElementById('place').value;
        const scheduleMemo = document.getElementById('memo').value;
        const categoryId = selectedCategory; // 이미 선택된 카테고리 ID

        const scheduleData = {
            scheduleTitle: scheduleTitle,
            scheduleStart: scheduleStart,
            scheduleEnd: scheduleEnd,
            schedulePlace: schedulePlace,
            scheduleMemo: scheduleMemo,
            categoryId: categoryId,
            userId: uid
        };

        fetch('/schedule/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(scheduleData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert('일정이 추가되었습니다.');
                    // 일정 추가 후 alert 확인 버튼을 누르면 모달을 닫음
                    setTimeout(() => {
                        closeModal();
                        handleClick({target: document.querySelector('.today')});
                    }, 0);
                } else {
                    alert('일정 추가에 실패했습니다.');
                    setTimeout(() => {
                        closeModal();
                    }, 0);
                }
            })
            .catch(error => console.error('Error:', error));
    });

    // 모달 닫기
    function closeModal() {
        smodal.style.display = 'none';

        // 폼 초기화
        document.getElementById('title').value = '';
        document.getElementById('sdate').value = '';
        document.getElementById('edate').value = '';
        document.getElementById('place').value = '';
        document.getElementById('memo').value = '';

        // 카테고리 버튼의 선택 상태 초기화
        const categoryButtons = document.querySelectorAll('.category-btn');
        categoryButtons.forEach(button => {
            button.classList.remove('selected');
            button.style.border = ''; // 회색 테두리 제거
        });

        selectedCategory = '';
        if(document.querySelector('.error-message')) {
            document.querySelector('.error-message').remove(); // 에러 메시지 삭제
        }
        location.reload();
    }

    // 모달 밖을 클릭하면 모달 닫기
    window.addEventListener("click", (event) => {
        if (event.target === smodal) {
            closeModal();
        }
    });
});
