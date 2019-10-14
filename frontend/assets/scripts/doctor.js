//тут прописать отправку формы при нажатии на кнопку


function treatmentSend() {
  closeTreatment();
  //впихнуть функцию отправки формы
}


function closeTreatment() {
  const treatwindow = document.getElementsByClassName('patients_treatment')[0];
  treatwindow.style.visibility = 'hidden';
}


function openTratment() {
  const treatwindow = document.getElementsByClassName('patients_treatment')[0];
  treatwindow.style.visibility = 'visible';

}


function toDocSend(){
  const docdoc = document.getElementsByClassName('another_doc')[0];
  docdoc.style.visibility = 'hidden';
  //тут еще функция отправки к другому врачу(отправка формы)

}

function openDocSend() {
  const docdoc = document.getElementsByClassName('another_doc')[0];
  docdoc.style.visibility = 'visible';

}



function showBanWindow() {
  const ban = document.getElementsByClassName('ban_user')[0];
  ban.style.visibility = 'visible';
}


function Report() {
  const ban = document.getElementsByClassName('ban_user')[0];
  ban.style.visibility = 'hidden';
  //сюда еще функцию отправки запроса о нарушении
}