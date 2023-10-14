$(function() {
    // 3일 뒤의 날짜와 요일을 가져오는 코드
    // 3일 뒤가 일요일일 경우 4일 뒤의 데이터를 가져옴
    let arrivalDate = '3일 후';

    // 현재 날짜를 가져옵니다.
    const today = new Date();

    // 3일 뒤의 날짜를 계산합니다.
    const nextThreeDays = new Date();
    nextThreeDays.setDate(today.getDate() + 3);

    // 일요일인 경우 4일 뒤의 날짜를 계산하여 월요일로 만듭니다.
    if (nextThreeDays.getDay() === 0) { // 0은 일요일을 나타냅니다.
        nextThreeDays.setDate(nextThreeDays.getDate() + 4);
        arrivalDate = '4일 후';
    }

    // 날짜와 요일을 계산합니다.
    const daysOfWeek = ["일", "월", "화", "수", "목", "금", "토"];
    const nextThreeDaysDayOfWeek = daysOfWeek[nextThreeDays.getDay()];

    // 날짜와 요일을 출력합니다.
    console.log("3일 후 날짜: " + nextThreeDays.toLocaleDateString());
    console.log("3일 후 요일: " + nextThreeDaysDayOfWeek);

    const de = arrivalDate + '(' + nextThreeDaysDayOfWeek + ') ' + (nextThreeDays.getMonth() + 1) + '/' + nextThreeDays.getDate() + ' 도착예정';
    $('.arrival').text(de);
});