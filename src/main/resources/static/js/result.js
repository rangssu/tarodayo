// 결과 페이지: 해석 텍스트 타이핑 효과
document.addEventListener('DOMContentLoaded', () => {
    const textEl = document.querySelector('.interpretation-text');
    if (!textEl) return;

    const fullText = textEl.textContent;
    textEl.textContent = '';

    let i = 0;
    const speed = 18;

    function type() {
        if (i < fullText.length) {
            textEl.textContent += fullText[i++];
            setTimeout(type, speed);
        }
    }

    setTimeout(type, 300);
});
