document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('search-input');
    const searchResults = document.getElementById('search-results');
    const historyResults = document.getElementById('history-results');
    const dropdownContainer = document.getElementById('dropdown-container');
    const searchButton = document.getElementById('search-button');
    const userId = 2; // ID người dùng cho API lịch sử
    const historyLabel = document.querySelector('.history-label');
    const outputDiv = document.getElementById('output');
    let currentFocus = -1; // Biến để theo dõi mục được chọn hiện tại
    let searchData = []; // Biến để lưu trữ dữ liệu tìm kiếm

    // Xử lý sự kiện nhập dữ liệu vào ô input
    searchInput.addEventListener('input', function() {
        const query = searchInput.value;
        if (query.length > 0) {
            fetchSearchResults(query);
            fetchHistoryResults(query);
        } else {
            clearDropdown();
        }
    });

    // Xử lý các phím bấm
    searchInput.addEventListener('keydown', function(e) {
        const items = dropdownContainer.querySelectorAll('li');
        if (e.key === 'ArrowDown') {
            // Khi nhấn phím xuống
            currentFocus++;
            addActive(items);
        } else if (e.key === 'ArrowUp') {
            // Khi nhấn phím lên
            currentFocus--;
            addActive(items);
        } else if (e.key === 'Enter') {
            // Khi nhấn phím Enter
            e.preventDefault();
            if (currentFocus > -1) {
                if (items) {
                    const selectedItem = items[currentFocus];
                    searchInput.value = selectedItem.textContent; // Lấy giá trị của mục được chọn
                    clearDropdown();
                    performSearch(selectedItem.textContent, selectedItem.dataset.id); // Thực hiện tìm kiếm với từ được chọn
                }
            } else {
                performSearch(searchInput.value); // Thực hiện tìm kiếm với từ trong ô input
                clearDropdown();
            }
        }
    });

    // Xử lý sự kiện click nút tìm kiếm
    searchButton.addEventListener('click', function() {
        performSearch(searchInput.value); // Thực hiện tìm kiếm với từ trong ô input
        clearDropdown();
    });

    // Xử lý sự kiện click vào mục trong dropdown
    searchResults.addEventListener('click', function(e) {
        if (e.target.tagName === 'LI') {
            const selectedItem = e.target;
            searchInput.value = selectedItem.textContent; // Lấy giá trị của mục được chọn
            clearDropdown();
            performSearch(selectedItem.textContent, selectedItem.dataset.id); // Thực hiện tìm kiếm với từ được chọn
        }
    });

    function addActive(items) {
        if (!items) return false;
        removeActive(items);
        if (currentFocus >= items.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = items.length - 1;
        items[currentFocus].classList.add('autocomplete-active');
    }

    function removeActive(items) {
        for (let i = 0; i < items.length; i++) {
            items[i].classList.remove('autocomplete-active');
        }
    }

    function clearDropdown() {
        searchResults.innerHTML = '';
        historyResults.innerHTML = '';
        dropdownContainer.style.display = 'none';
        historyLabel.style.display = 'none';
        searchInput.classList.remove('dropdown-visible');
        currentFocus = -1; // Reset lại vị trí hiện tại
    }

    function fetchSearchResults(query) {
        const myHeaders = new Headers();
        myHeaders.append("Cookie", "JSESSIONID=36E503BFA7CDE19F5C1E39E373B6E6EF");

        const requestOptions = {
            method: "GET",
            headers: myHeaders,
            redirect: "follow"
        };

        fetch(`http://localhost:${port}/api/v1/searchWord/keyword?keyword=${query}&limit=7`, requestOptions)
            .then(response => response.json())
            .then(data => {
                searchData = data; // Lưu trữ dữ liệu tìm kiếm
                displaySearchResults(data);
            })
            .catch(error => console.error('Error:', error));
    }

    function fetchHistoryResults(query) {
        const myHeaders = new Headers();
        myHeaders.append("Cookie", "JSESSIONID=36E503BFA7CDE19F5C1E39E373B6E6EF");

        const requestOptions = {
            method: "GET",
            headers: myHeaders,
            redirect: "follow"
        };

        fetch(`http://localhost:${port}/api/v1/searchWord/user?userId=${userId}&keyword=${query}&limit=3`, requestOptions)
            .then(response => response.json())
            .then(data => {
                displayHistoryResults(data);
            })
            .catch(error => console.error('Error:', error));
    }

    function displaySearchResults(results) {
        searchResults.innerHTML = '';
        results.forEach(result => {
            const li = document.createElement('li');
            li.textContent = result.name; // Thay đổi theo cấu trúc dữ liệu của bạn
            li.dataset.id = result.id; // Lưu ID vào dataset
            li.addEventListener('click', function() {
                searchInput.value = result.name;
                clearDropdown();
                performSearch(result.name, result.id); // Thực hiện tìm kiếm với từ được chọn
            });
            searchResults.appendChild(li);
        });
        checkDropdownDisplay();
    }

    function displayHistoryResults(results) {
        historyResults.innerHTML = '';
        if (results.length > 0) {
            historyLabel.style.display = 'block';
            results.forEach(result => {
                const li = document.createElement('li');
                li.textContent = result.name; // Thay đổi theo cấu trúc dữ liệu của bạn
                li.dataset.id = result.id; // Lưu ID vào dataset
                li.addEventListener('click', function() {
                    searchInput.value = result.name;
                    clearDropdown();
                    performSearch(result.name, result.id); // Thực hiện tìm kiếm với từ được chọn
                });
                historyResults.appendChild(li);
            });
        } else {
            historyLabel.style.display = 'none';
        }
        checkDropdownDisplay();
    }

    function checkDropdownDisplay() {
        if (searchResults.innerHTML !== '' || historyResults.innerHTML !== '') {
            dropdownContainer.style.display = 'block';
            searchInput.classList.add('dropdown-visible'); // Thêm lớp để bỏ bo tròn góc dưới
        } else {
            clearDropdown();
        }
    }

    function performSearch(query, id) {
        if (query.trim() !== '') {
            const found = searchData.find(item => item.name.toLowerCase() === query.toLowerCase());
            if (found || id) {
                const searchId = found ? found.id : id;
                updateURL(query, searchId); // Cập nhật URL với từ khóa tìm kiếm và ID
                saveSearchHistory(searchId);
                displayResult(searchId, query); // Hiển thị kết quả (tùy chọn)
            } else {
                updateURL(query, null); // Cập nhật URL với từ khóa tìm kiếm
                displayResult(null, query);
            }
        }
    }

    function displayResult(id, name) {
        if (id) {
            outputDiv.innerHTML = `ID: ${id}, Name: ${name}`;
        } else {
            outputDiv.innerHTML = `No results for: ${name}`;
        }
    }

    function saveSearchHistory(wordId) {
        const raw = JSON.stringify({
            "userId": userId,
            "wordId": wordId
        });

        const requestOptions = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Cookie": "JSESSIONID=36E503BFA7CDE19F5C1E39E373B6E6EF"
            },
            body: raw,
            redirect: "follow"
        };

        fetch(`http://localhost:${port}/api/v1/searchWord/save?userId=${userId}&wordId=${wordId}`, requestOptions)
            .then(response => response.text())
            .then(result => console.log('History saved:', result))
            .catch(error => console.error('Error:', error));
    }

    function updateURL(query, id) {
        if (id) {
            const newURL = `index.html?search=${encodeURIComponent(query)}&id=${id}`;
            window.location.href = newURL; // Chuyển hướng đến index.html
        } else {
            const newURL = `index.html?search=${encodeURIComponent(query)}`;
            window.location.href = newURL; // Chuyển hướng đến index.html
        }
    }
});