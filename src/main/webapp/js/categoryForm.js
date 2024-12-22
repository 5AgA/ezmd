document.addEventListener("DOMContentLoaded", () => {
    const cmodal = document.querySelector(".category-form-modal");
    const cmodalBtn = document.getElementById("category-modal-btn");

    // 카테고리 모달 열기
    cmodalBtn.addEventListener("click", () => {
        cmodal.style.display = "flex";
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
    };
});