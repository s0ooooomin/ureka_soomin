// 날짜 : 10 미만은 앞에 0 붙임
function makeDateStr(year, month, day, type){
    return year + type + ( (month < 10) ? '0' + month : month ) + type + ( (day < 10) ? '0' + day : day );
}
// 시간 : 10 미만은 앞에 0 붙임
function makeTimeStr(hour, minute, second, type){
    return hour + type + ( (minute < 10) ? '0' + minute : minute ) + type + ( (second < 10) ? '0' + second : second );
}