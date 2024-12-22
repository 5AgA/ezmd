document.addEventListener("DOMContentLoaded", () => {
    const dateCells = document.querySelectorAll('.calendar-table td');
    const timeButtons = document.querySelectorAll('.time-button');

    const selectedDateInput = document.getElementById('selectedDate');
    const selectedTimeInput = document.getElementById('selectedTime');

    dateCells.forEach(cell => {
        cell.addEventListener('click', function () {
            if (this.innerText.trim() !== "") {
                // 모든 날짜 선택 해제
                dateCells.forEach(c => c.classList.remove('selected'));
                // 현재 선택한 날짜에 selected 클래스 추가
                this.classList.add('selected');
                // 히든 필드에 날짜 저장 (2024년 12월 가정)
                selectedDateInput.value = "2024-12-" + (this.innerText.trim().length === 1 ? "0" + this.innerText.trim() : this.innerText.trim());
            }
        });
    });

    timeButtons.forEach(btn => {
        btn.addEventListener('click', function () {
            // 모든 시간 선택 해제
            timeButtons.forEach(b => b.classList.remove('selected'));
            // 현재 선택한 시간에 selected 클래스 추가
            this.classList.add('selected');
            // 히든 필드에 시간 저장
            selectedTimeInput.value = this.innerText.trim();
        });
    });

});