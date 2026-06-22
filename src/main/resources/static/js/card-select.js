const MAX_SELECT = 3;
let selectedIndexes = [];

function selectCard(slot) {
    const index = parseInt(slot.dataset.index);

    if (slot.classList.contains('selected')) {
        // 선택 해제
        slot.classList.remove('selected');
        selectedIndexes = selectedIndexes.filter(i => i !== index);
        updateUI();
        return;
    }

    if (selectedIndexes.length >= MAX_SELECT) return;

    slot.classList.add('selected');
    selectedIndexes.push(index);
    updateUI();

    if (selectedIndexes.length === MAX_SELECT) {
        setTimeout(submitForm, 500);
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
    // 기존 hidden input 제거
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
