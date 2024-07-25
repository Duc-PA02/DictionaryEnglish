document.addEventListener("DOMContentLoaded", function() {
    const textarea = document.getElementById('source-text');
    const maxChars = 700;

    textarea.addEventListener('input', function() {
        // Giới hạn số ký tự
        if (this.value.length > maxChars) {
            this.value = this.value.substring(0, maxChars);
            showCustomAlert('You have reached the maximum number of characters allowed.');
        }

        // Reset height to auto to get the natural height
        this.style.height = 'auto';
        // Set height to scrollHeight to ensure it expands with content
        this.style.height = `${this.scrollHeight}px`;
    });

    // Thực hiện một lần khi trang được tải để đảm bảo chiều cao đúng ngay từ đầu
    textarea.dispatchEvent(new Event('input'));

    function showCustomAlert(message) {
        let alertBox = document.getElementById('custom-alert');
        if (!alertBox) {
            alertBox = document.createElement('div');
            alertBox.id = 'custom-alert';
            alertBox.className = 'custom-alert';
            document.body.appendChild(alertBox);
        }
        alertBox.textContent = message;
        alertBox.classList.add('show');
        setTimeout(() => {
            alertBox.classList.remove('show');
        }, 2000); // Thời gian hiển thị thông báo, ví dụ 2000ms (2 giây)
    }
});

document.addEventListener("DOMContentLoaded", function() {
    function setupCustomSelect(selectElement) {
        var selected = selectElement.querySelector('.select-selected');
        var items = selectElement.querySelector('.select-items');

        selected.addEventListener('click', function() {
            items.classList.toggle('select-hide');
        });

        var options = items.querySelectorAll('.select-option');
        options.forEach(function(option) {
            option.addEventListener('click', function() {
                var value = this.getAttribute('data-value');
                var text = this.innerText;

                selected.innerText = text;
                items.classList.add('select-hide');

                // Cập nhật giá trị của thẻ select gốc nếu cần
                var originalSelect = selectElement.querySelector('select');
                if (originalSelect) {
                    originalSelect.value = value;
                }
            });
        });
    }

    // Thiết lập dropdown cho tất cả các custom select
    var customSelects = document.querySelectorAll('.custom-select');
    customSelects.forEach(setupCustomSelect);

    // Đóng dropdown khi nhấp bên ngoài
    document.addEventListener('click', function(e) {
        if (!e.target.closest('.custom-select')) {
            customSelects.forEach(function(select) {
                var items = select.querySelector('.select-items');
                items.classList.add('select-hide');
            });
        }
    });
});

function playAudio(type) {
    let audioElement;
    if (type === 'source') {
        audioElement = document.getElementById('source-audio');
    } else if (type === 'target') {
        audioElement = document.getElementById('target-audio');
    }
    if (audioElement && audioElement.src) {
        audioElement.play();
    } else {
        showCustomAlert('No audio available.');
    }
}

function copyText(type) {
    let textToCopy;
    if (type === 'source') {
        textToCopy = document.getElementById('source-text').value;
    } else if (type === 'target') {
        textToCopy = document.getElementById('translated-text').innerText;
    }

    if (navigator.clipboard) {
        navigator.clipboard.writeText(textToCopy).then(() => {
            showCustomAlert('Copied to clipboard!');
        }).catch(err => {
            console.error('Failed to copy text: ', err);
        });
    } else {
        // Fallback for older browsers
        let textarea = document.createElement('textarea');
        textarea.value = textToCopy;
        document.body.appendChild(textarea);
        textarea.select();
        document.execCommand('copy');
        document.body.removeChild(textarea);
        showCustomAlert('Copied to clipboard!');
    }
}

function showCustomAlert(message) {
    let alertBox = document.getElementById('custom-alert');
    if (!alertBox) {
        alertBox = document.createElement('div');
        alertBox.id = 'custom-alert';
        alertBox.className = 'custom-alert';
        document.body.appendChild(alertBox);
    }
    alertBox.textContent = message;
    alertBox.classList.add('show');
    setTimeout(() => {
        alertBox.classList.remove('show');
    }, 2000); // Thời gian hiển thị thông báo, ví dụ 2000ms (2 giây)
}

document.addEventListener("DOMContentLoaded", function() {
    const requestOptions = {
        method: "GET",
        redirect: "follow"
    };

    fetch("http://localhost:8083/api/v1/translate/language", requestOptions)
        .then((response) => response.json())
        .then((languages) => {
            const selectItems = document.querySelector('.custom-select .select-items');
            const selectSelected = document.querySelector('.custom-select .select-selected');

            // Clear existing options
            selectItems.innerHTML = '';

            // Add new options
            languages.forEach(language => {
                const optionElement = document.createElement('div');
                optionElement.classList.add('select-option');
                optionElement.innerText = language.replace(/_/g, ' '); // Replace underscores with spaces and convert to lowercase

                optionElement.addEventListener('click', function() {
                    selectSelected.innerText = optionElement.innerText;
                    selectItems.classList.add('select-hide');

                    // Update original select element value if needed
                    const originalSelect = document.querySelector('.custom-select select');
                    if (originalSelect) {
                        originalSelect.value = language;
                    }
                });

                selectItems.appendChild(optionElement);
            });

            // Set default selected option
            if (languages.length > 0) {
                selectSelected.innerText = languages[0].replace(/_/g, ' ');
            }
        })
        .catch((error) => console.error('Error fetching languages:', error));
});

document.querySelector('.arrow-container').addEventListener('click', function() {
    const inputText = document.getElementById('source-text').value;

    // Lấy phần tử đã được chọn
    const selectedOption = document.querySelector('.select-selected');

    // Lấy nội dung văn bản từ phần tử đã chọn
    const targetLanguage = selectedOption ? selectedOption.innerText : '';

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const raw = JSON.stringify({ "inputText": inputText });

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch(`http://localhost:8083/api/v1/translate/${targetLanguage}`, requestOptions)
        .then(response => response.json())
        .then(result => {
            document.getElementById('translated-text').innerText = result.translatedText;
            // Cập nhật audio sources
            updateAudioSources(result.inputVoice, result.translatedVoice);
        })
        .catch(error => console.log('error', error));
});

function updateAudioSources(inputVoice, translatedVoice) {
    const sourceAudioElement = document.getElementById('source-audio');
    const targetAudioElement = document.getElementById('target-audio');

    if (sourceAudioElement) {
        sourceAudioElement.src = inputVoice;
    }

    if (targetAudioElement) {
        targetAudioElement.src = translatedVoice;
    }
}

function updateAudioSources(inputVoice, translatedVoice) {
    const sourceAudioElement = document.getElementById('source-audio');
    const targetAudioElement = document.getElementById('target-audio');

    // Giả sử tệp âm thanh nằm trong thư mục 'audio' trên cùng cấp với thư mục HTML
    const basePath = "../../../../"; // Cập nhật đường dẫn cơ sở này nếu cần

    if (sourceAudioElement) {
        sourceAudioElement.src = `${basePath}${inputVoice}`;
    }

    if (targetAudioElement) {
        targetAudioElement.src = `${basePath}${translatedVoice}`;
    }
}