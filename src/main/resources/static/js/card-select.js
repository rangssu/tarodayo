const MAX_SELECT = 3;
let selectedIndexes = [];
let audioCtx = null;

function getAudioCtx() {
    if (!audioCtx) {
        audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    }
    return audioCtx;
}

function playFlipSound() {
    try {
        const ctx = getAudioCtx();
        const osc = ctx.createOscillator();
        const gain = ctx.createGain();
        osc.connect(gain);
        gain.connect(ctx.destination);
        osc.type = 'sine';
        osc.frequency.setValueAtTime(700, ctx.currentTime);
        osc.frequency.exponentialRampToValueAtTime(350, ctx.currentTime + 0.25);
        gain.gain.setValueAtTime(0.12, ctx.currentTime);
        gain.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.3);
        osc.start(ctx.currentTime);
        osc.stop(ctx.currentTime + 0.3);
    } catch (e) {}
}

function playSelectSound() {
    try {
        const ctx = getAudioCtx();
        [523, 659, 784].forEach((freq, i) => {
            const osc = ctx.createOscillator();
            const gain = ctx.createGain();
            osc.connect(gain);
            gain.connect(ctx.destination);
            osc.type = 'sine';
            osc.frequency.value = freq;
            const t = ctx.currentTime + i * 0.08;
            gain.gain.setValueAtTime(0.1, t);
            gain.gain.exponentialRampToValueAtTime(0.001, t + 0.35);
            osc.start(t);
            osc.stop(t + 0.4);
        });
    } catch (e) {}
}

function createSparkles(x, y) {
    const symbols = ['✦', '★', '✧', '·', '✦', '✧'];
    const colors = ['#d4a8ff', '#a855f7', '#ffffff', '#ffb87a', '#c9aee8'];
    symbols.forEach((sym, i) => {
        const el = document.createElement('div');
        el.className = 'sparkle';
        el.textContent = sym;
        el.style.left = (x + (Math.random() - 0.5) * 70) + 'px';
        el.style.top  = (y + (Math.random() - 0.5) * 50) + 'px';
        el.style.color = colors[Math.floor(Math.random() * colors.length)];
        el.style.animationDelay = (i * 0.06) + 's';
        document.body.appendChild(el);
        setTimeout(() => el.remove(), 1000);
    });
}

function selectCard(slot, event) {
    const index = parseInt(slot.dataset.index);

    if (slot.classList.contains('selected')) {
        slot.classList.remove('selected');
        selectedIndexes = selectedIndexes.filter(i => i !== index);
        updateUI();
        return;
    }

    if (selectedIndexes.length >= MAX_SELECT) return;

    playFlipSound();

    const rect = slot.getBoundingClientRect();
    const cx = rect.left + rect.width / 2;
    const cy = rect.top + rect.height / 2;
    createSparkles(cx, cy);

    slot.classList.add('selected');
    selectedIndexes.push(index);
    updateUI();

    if (selectedIndexes.length === MAX_SELECT) {
        playSelectSound();
        setTimeout(submitForm, 700);
    }
}

function updateUI() {
    document.getElementById('count').textContent = selectedIndexes.length;

    const allSlots = document.querySelectorAll('.card-slot');
    if (selectedIndexes.length >= MAX_SELECT) {
        allSlots.forEach(slot => {
            if (!slot.classList.contains('selected')) {
                slot.classList.add('dimmed');
            }
        });
    } else {
        allSlots.forEach(slot => slot.classList.remove('dimmed'));
    }
}

function submitForm() {
    const form = document.getElementById('selectForm');
    form.querySelectorAll('input[name="selectedIndexes"]').forEach(el => el.remove());

    selectedIndexes.forEach(idx => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'selectedIndexes';
        input.value = idx;
        form.appendChild(input);
    });

    form.submit();
}
