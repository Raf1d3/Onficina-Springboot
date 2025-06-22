// configuracao do money mask
const moneyMaskOptions = {
  prefix: "",
  suffix: "",
  fixed: true,
  fractionDigits: 2,
  decimalSeparator: ",",
  thousandsSeparator: ".",
  autoCompleteDecimal: true,
};

const Toast = Swal.mixin({
  toast: true,
  position: "center-end",
  iconColor: "white",
  customClass: {
    popup: "colored-toast",
  },
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  grow: "row",
  didOpen: (toast) => {
    toast.onmouseenter = Swal.stopTimer;
    toast.onmouseleave = Swal.resumeTimer;
  },
});

atualizarCodigoNaPagina();

function atualizarCodigoNaPagina() {
  prepararComponentesMoney();
  prepararComponentesData();
  prepararComponentesHora();
  prepararConfirmacoes();
  mostrarNotificacaoFromHTML();
}


//Busca campos input com a classe componentemoney na pagina atual e liga-os com
// o componente mask money.
function prepararComponentesMoney() {
  let inputsMoney = document.querySelectorAll(".componentemoney");
  inputsMoney.forEach(function (input) {
    SimpleMaskMoney.setMask(input, moneyMaskOptions);
  });
}

//Busca campos input com a classe componentedata na pagina atual e liga-os com
// o componente date picker. Os input devem ter u ID
function prepararComponentesData() {
  let inputsData = document.querySelectorAll(".componentedata");
  inputsData.forEach(function (input) {
    var datePicker;
    if (input.value !== "") {
      const dateParts = input.value.split("/");
      const dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
      datePicker = MCDatepicker.create({
        el: String("#" + input.id),
        dateFormat: "dd/mm/yyyy",
        selectedDate: dateObject,
        customWeekDays: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
        customMonths: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
        customClearBTN: "Limpar",
        customCancelBTN: "Cancelar",
        theme: {
          theme_color: "rgb(31 41 55)",
        },
      });
    } else {
      datePicker = MCDatepicker.create({
        el: String("#" + input.id),
        dateFormat: "dd/mm/yyyy",
        customWeekDays: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
        customMonths: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
        customClearBTN: "Limpar",
        customCancelBTN: "Cancelar",
        theme: {
          theme_color: "rgb(31 41 55)",
        },
      });
    }
    datePicker.onOpen(() => {
      if (input.value !== "" && input.value !== datePicker.getFormatedDate()) {
        const dateParts = input.value.split("/");
        const dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
        datePicker.setFullDate(dateObject);
      }
    });
  });
}

//Busca campos input com a classe componentehora na pagina atual e liga-os com
// o componente time picker.
function prepararComponentesHora() {
  let inputsHora = document.querySelectorAll(".componentehora");
  inputsHora.forEach(function (input) {
    const dialog = new mdDateTimePicker.default({
      type: "time",
      mode: true,
      inner24: true,
      cancel: "Cancelar",
    });
    input.addEventListener("click", function () {
      dialog.toggle();
    });
    dialog.trigger = input;
    input.addEventListener("onOk", function () {
      this.value = dialog.time.format("HH:mm");
    });
  });
}

//Busca por botoes com a classe confirmacao e usa o SweetAlert2 para pedir uma
// confirmacao do usuario antes de efetuar o submit do formulario pai do botao.
//O valor do atributo hx-confirm do form eh exibido na pergunta.
//Caso o usuario aceite, o formulario eh submetido, caso cancele ou feche nada acontece.
function prepararConfirmacoes() {
  let forms = document.querySelectorAll("[hx-confirm]");
  forms.forEach(function (form) {
    form.addEventListener("htmx:confirm", function (e) {
      e.preventDefault();
      Swal.fire({
        title: "Você tem certeza?",
        text: e.detail.question,
        icon: "warning",
        showCancelButton: true,
        cancelButtonText: "Cancelar",
        confirmButtonText: "Remover",
        confirmButtonColor: "#3085d6",
      }).then((result) => {
        if (result.isConfirmed) {
          e.detail.issueRequest(true); // true to skip the built-in window.confirm()
        }
      });
    });
  });
}

//Busca por um input hidden com o id mensagem no HTML da página atual.
//Caso encontre, busca por um input hidden com o id tipo e um input hidden com o id intervalo.
//Mostra uma notificao do AlertifyJS desse tipo com essa mensagem por esse intervalor.
function mostrarNotificacaoFromHTML() {
  let inputMensagem = document.getElementById("mensagem");
  if (inputMensagem !== null && inputMensagem.value !== "") {
    let mensagem = inputMensagem.value;
    let tipo = document.getElementById("tipo").value;
    let intervalo = document.getElementById("intervalo").value;
    Toast.fire({
      icon: tipo,
      title: mensagem,
      timer: intervalo,
    });
  }
}

// Para aplicar o codigoninjadogodoi no HTML que foi colocado na pagina
// usando htmx
// document.body.addEventListener("htmlAtualizado", function (evt) {
//   atualizarCodigoNaPagina();
// });

htmx.on("htmx:afterSettle", function(evt) {
    atualizarCodigoNaPagina();
});


// Para a parte de segurança funcionar com CSRF
var csrfHeader = null;
var csrfToken = null;

function atualizarCSRF() {
  csrfHeader = document.querySelector("input[name=csrf-header]").value;
  csrfToken = document.querySelector("input[name=csrf-token]").value;
}

document.body.addEventListener("htmx:configRequest", (evt) => {
  evt.detail.headers["accept"] = "text/html-partial";
  if (evt.detail.verb !== "get") {
    atualizarCSRF();
    if (csrfHeader != null && csrfToken != null) {
      evt.detail.headers[csrfHeader] = csrfToken;
    }
  }
});