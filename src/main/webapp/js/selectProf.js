document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.querySelector(".search-input");
    const searchedBox = document.querySelector(".searched-box");

    // 검색어 입력 시 자동으로 서버에 요청
    searchInput.addEventListener("input", function() {
        const keyword = searchInput.value.trim();

        if (keyword.length >= 2) {
            fetch(`/profs/search?keyword=${keyword}`)
                .then((response) => response.json())
                .then((data) => {
                    searchedBox.innerHTML = ''; // 이전 검색 결과를 지우기

                    if (data.length === 0) {
                        searchedBox.innerHTML = "<p>검색 결과가 없습니다.</p>";
                        return;
                    }

                    // 검색된 교수 항목을 동적으로 생성하여 추
                    data.forEach((professor) => {
                        const professorItem = document.createElement("div");
                        professorItem.classList.add("professor-item");
                        professorItem.dataset.id = professor.professorId;

                        const professorName = document.createElement("span");
                        professorName.classList.add("professor-name");
                        professorName.textContent = professor.name;

                        const professorDept = document.createElement("span");
                        professorDept.classList.add("professor-dept");
                        professorDept.textContent = professor.dept;

                        professorItem.appendChild(professorName);
                        professorItem.appendChild(professorDept);

                        searchedBox.appendChild(professorItem);
                    });
                })
                .catch((error) => {
                    console.error('Error fetching professor data:', error);
                });
        } else {
            searchedBox.innerHTML = ''; // 검색어가 짧으면 결과를 지웁니다.
        }
    });

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

    // 교수 항목 클릭 시 선택
    $(document).on("click", ".professor-item", function () {
        const professorId = $(this).data("id");

        $(".professor-item").removeClass("selected");
        $(".professor-name").removeClass("selected");
        $(this).addClass("selected");
        // 교수 항목의 dataset.id로 선택된 상태로 변경
        $(".professor-name").each(function () {
            if ($(this).data("id") === professorId) {
                $(this).addClass("selected");
            }
        });

    });

    // 교수 항목 클릭 시 선택 처리
    $(document).on("click", ".professor-name", function () {
        const professorId = $(this).data("id");

        // 이미 선택된 교수 제거
        $(".professor-item").removeClass("selected");
        $(".professor-name").removeClass("selected");

        // 클릭한 교수 선택
        $(this).addClass("selected");

        // 교수 항목의 dataset.id로 선택된 상태로 변경
        $(".professor-item").each(function () {
            const itemId = $(this).find(".professor-name").data("id");
            if (itemId === professorId) {
                $(this).addClass("selected");
            }
        });
    });

// Set hidden input values and submit form
    $(".select-button").on("click", function (e) {
        e.preventDefault(); // 기본 제출 동작 방지

        const selectedProfessor = $(".professor-name.selected");
        const professorName = selectedProfessor.text();
        const professorId = selectedProfessor.get(0).dataset.id;

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