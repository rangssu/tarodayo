function playRevealSound() {
    try {
        const ctx = new (window.AudioContext || window.webkitAudioContext)();
        [392, 523, 659, 784, 1047].forEach((freq, i) => {
            const osc  = ctx.createOscillator();
            const gain = ctx.createGain();
            osc.connect(gain);
            gain.connect(ctx.destination);
            osc.type = 'sine';
            osc.frequency.value = freq;
            const t = ctx.currentTime + i * 0.11;
            gain.gain.setValueAtTime(0.08, t);
            gain.gain.exponentialRampToValueAtTime(0.001, t + 0.5);
            osc.start(t);
            osc.stop(t + 0.55);
        });
    } catch (e) {}
}

document.addEventListener('DOMContentLoaded', () => {
    // 결과 카드 등장 시 효과음
    setTimeout(playRevealSound, 150);

    // AI 해석 타이핑 효과
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

    setTimeout(type, 950);
});
