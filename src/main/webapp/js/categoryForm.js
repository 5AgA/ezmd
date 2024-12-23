document.addEventListener("DOMContentLoaded", () => {
    const cmodal = document.querySelector(".category-form-modal");
    const cmodalBtn = document.getElementById("category-modal-btn");
    const actionButtons = document.querySelector(".action-buttons");
    let categories = [];

    fetch(`/schedule/categories`)
        .then(response => response.json())
        .then(data => {
            categories = data; // 카테고리 정보를 배열로 저장
        })
        .catch(error => console.error('Error fetching categories:', error));

    // 카테고리 모달 열기
    cmodalBtn.addEventListener("click", () => {
        cmodal.style.display = "flex";
        actionButtons.style.display = "none";
        document.getElementById("category-name").focus();
    });

    // 모달 밖을 클릭하면 모달 닫기
    window.addEventListener("click", (event) => {
        if (event.target === cmodal) {
            closeModal();
        }
    });

    // 모달 닫기
    const closeModal = () => {
        cmodal.style.display = "none";
        document.getElementById("category-name").value = "";
        document.getElementById("category-color").value = "#ffffff";
        if(document.querySelector('.error-message')) {
            document.querySelector('.error-message').remove(); // 에러 메시지 삭제
        }
        location.reload();
    };

    document.getElementById('category-form').addEventListener('submit', (event) => {
        event.preventDefault();

       const categoryName = document.getElementById('category-name').value;
       const categoryColor = document.getElementById('category-color').value;

       // 카테고리 이름 중복 체크
       if (categories.includes(categoryName)) {
           const cnameError = document.createElement('p');
           cnameError.className = 'error-message';
           cnameError.textContent = '이미 존재하는 카테고리입니다.';
           cnameError.style.color = 'red';
           document.getElementById("category-name").appendChild(cnameError);
           return;
       }

       const categoryData = {
           categoryName: categoryName,
           categoryColor: categoryColor,
           userId: uid
       };

        fetch('/category/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(categoryData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert('카테고리가 추가되었습니다.');
                    // 일정 추가 후 alert 확인 버튼을 누르면 모달을 닫음
                    setTimeout(() => {
                        closeModal();
                    }, 0);
                } else {
                    alert('카테고리 추가에 실패했습니다.');
                    setTimeout(() => {
                        closeModal();
                    }, 0);
                }
            })
            .catch(error => console.error('Error:', error));
    });
});