document.addEventListener("DOMContentLoaded", () => {
// Department and professor data
    $(document).ready(function () {
        fetch('/profs/view')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch professor data.');
                }
                return response.json();
            })
            .then((data) => {
                // 학과별 교수님 분류
                const departments = {};

                data.forEach((professor) => {
                    if (!departments[professor.dept]) {
                        departments[professor.dept] = [];
                    }
                    departments[professor.dept].push(professor);
                });

                // DOM에 추가
                const $departmentsDiv = $('.departments');
                $departmentsDiv.empty(); // 기존 내용을 제거

                Object.keys(departments).forEach((dept) => {
                    const departmentHtml = `
                    <div>
                        <button class="department-button">
                            ${dept}
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            ${departments[dept]
                        .map(
                            (prof) =>
                                `<li class="professor-name" data-id="${prof.professorId}">${prof.name}</li>`
                        )
                        .join('')}
                        </ul>
                    </div>
                `;
                    $departmentsDiv.append(departmentHtml);
                });
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('교수님 목록을 불러오는 데 실패했습니다.');
            });
    });

// Toggle department professors
    $(document).on("click", ".department-button", function () {
        const $professorList = $(this).next(".professor-names");
        $professorList.toggleClass("hidden");
        const $triangle = $(this).find(".triangle");
        $triangle.text($professorList.hasClass("hidden") ? "▶" : "▼");
    });

// Highlight selected professor
    $(document).on("click", ".professor-name", function () {
        $(".professor-name").removeClass("selected");
        $(this).addClass("selected");
    });

// Set hidden input values and submit form
    $(".select-button").on("click", function (e) {
        e.preventDefault(); // 기본 제출 동작 방지

        const $selectedProfessor = $(".professor-name.selected");
        const professorName = $selectedProfessor.text();
        const professorId = "30000010";

        if (!professorName || !professorId) {
            alert("선택된 교수가 없습니다.");
            return;
        }

        // 숨겨진 input 필드에 값 설정
        $("#professorId").val(professorId);
        $("#professorName").val(professorName);

        // 폼 제출
        $("#professorForm").submit();
    });
});