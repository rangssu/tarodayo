// 메인 페이지: 별 파티클 효과
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('.question-form');
    form.addEventListener('submit', (e) => {
        const textarea = document.getElementById('question');
        if (!textarea.value.trim()) {
            e.preventDefault();
            textarea.focus();
        }
    });
});
