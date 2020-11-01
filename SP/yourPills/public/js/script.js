function onlyInt(value) {
    value = value.replace(/[^0-9.]/g, '');
    value = value.replace(/(\..*)\./g, '$1');
    return value;
}

function onlyIntAnd24(value) {
    value = value.replace(/[^0-9.]/g, '');
    value = value.replace(/(\..*)\./g, '$1');
    if (value > 24) {
        value = 24;
    }
    return value;
}