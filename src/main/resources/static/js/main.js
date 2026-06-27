document.addEventListener('DOMContentLoaded', () => {
    const textarea = document.getElementById('question');
    const radios   = document.querySelectorAll('input[name="spreadType"]');
    const form     = document.querySelector('.question-form');

    const PLACEHOLDERS = {
        three: '예: 지금 하는 일이 잘 될까요? / 새로운 사람과의 관계는 어떻게 될까요?',
        today: '오늘 특별히 궁금한 것이 있다면 적어주세요. (선택 사항)'
    };

    function updateForm(spreadType) {
        if (spreadType === 'today') {
            textarea.removeAttribute('required');
            textarea.placeholder = PLACEHOLDERS.today;
        } else {
            textarea.setAttribute('required', '');
            textarea.placeholder = PLACEHOLDERS.three;
        }
    }

    radios.forEach(radio => {
        radio.addEventListener('change', () => updateForm(radio.value));
    });

    form.addEventListener('submit', (e) => {
        const spreadType = document.querySelector('input[name="spreadType"]:checked')?.value || 'three';
        if (spreadType !== 'today' && !textarea.value.trim()) {
            e.preventDefault();
            textarea.focus();
        }
    });
});
