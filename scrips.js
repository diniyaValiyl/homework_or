const mode = [
    {
        name: "Project 0",
        image: "foto/7.jpg",
    },
    {
        name: "Project 1", 
        image: "foto/6.jpg",
    },
    {
        name: "Project 2",
        image: "foto/5.jpg",
    }
];

let currentIndex = 0;
const Count = mode.length;

function initSlider() {
    const arrowLeft = document.querySelector("#arrowLeft");
    const arrowRight = document.querySelector("#arrowRight");
    
    if (arrowLeft && arrowRight) {
        showSlide(currentIndex);
        
        arrowLeft.addEventListener("click", () => {
            currentIndex = (currentIndex - 1 + Count) % Count;
            showSlide(currentIndex);
        });

        arrowRight.addEventListener("click", () => {
            currentIndex = (currentIndex + 1) % Count;
            showSlide(currentIndex);
        });
    }
}

function showSlide(index) {
    const cSlide = mode[index];
    const nextIndex = (index + 1) % Count;
    const nextSlide = mode[nextIndex];
    
    const cardText1 = document.querySelector(".card-text1");
    const cardImg1 = document.querySelector(".card-img-top1");
    
    if (cardText1 && cardImg1) {
        cardText1.textContent = cSlide.name;
        cardImg1.src = cSlide.image;
        cardImg1.alt = cSlide.name;
    }
    
    const cardText2 = document.querySelector(".card-text2");
    const cardImg2 = document.querySelector(".card-img-top2");
    
    if (cardText2 && cardImg2) {
        cardText2.textContent = nextSlide.name;
        cardImg2.src = nextSlide.image;
        cardImg2.alt = nextSlide.name;
    }
}

const themeBtn = document.getElementById('theme-toggle');
if (themeBtn) {
    const savedTheme = localStorage.getItem('theme') || 'light';
    
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
        themeBtn.textContent = 'Светлая тема';
    }
    
    themeBtn.onclick = function() {
        const isDark = document.body.classList.toggle('dark-theme');
        if (isDark) {
            localStorage.setItem('theme', 'dark');
            themeBtn.textContent = 'Светлая тема';
        } else {
            localStorage.setItem('theme', 'light');
            themeBtn.textContent = 'Темная тема';
        }
    }
}




function initTimer() {
    const elements = {
        days: document.querySelector('.timer__days'),
        hours: document.querySelector('.timer__hours'),
        minutes: document.querySelector('.timer__minutes'),
        seconds: document.querySelector('.timer__seconds')
    };
    
    if (!elements.days || !elements.hours || !elements.minutes || !elements.seconds) {
        console.log('Элементы таймера не найдены');
        return;
    }
    
    const currentYear = new Date().getFullYear();
    let birthday = new Date(currentYear, 9, 23); 
    
    if (new Date() > birthday) {
        birthday = new Date(currentYear + 1, 9, 23);
    }
    
    console.log('День рождения:', birthday);
    
    const updateTimer = () => {
        const now = new Date();
        const diff = birthday - now;
        
        console.log('Разница:', diff);
        
        if (diff <= 0) {
            clearInterval(timerId);
            elements.days.textContent = '00';
            elements.hours.textContent = '00';
            elements.minutes.textContent = '00';
            elements.seconds.textContent = '00';
            return;
        }
        
        const days = Math.floor(diff / (1000 * 60 * 60 * 24));
        const hours = Math.floor((diff / (1000 * 60 * 60)) % 24);
        const minutes = Math.floor((diff / (1000 * 60)) % 60);
        const seconds = Math.floor((diff / 1000) % 60);
        
        console.log('Время:', days, hours, minutes, seconds);
        
        elements.days.textContent = String(days).padStart(2, '0');
        elements.hours.textContent = String(hours).padStart(2, '0');
        elements.minutes.textContent = String(minutes).padStart(2, '0');
        elements.seconds.textContent = String(seconds).padStart(2, '0');
    };
    
    updateTimer();
    const timerId = setInterval(updateTimer, 1000);
    
    console.log('Таймер запущен');
}

const loadBtn = document.getElementById('load-content-btn');
if (loadBtn) {
    const container = document.getElementById('content-container');
    const facts = [
        "Я родилась 23 октября 2006 года",
        "Учусь в гимназии №96 в Казани",
        "Сдала ЕГЭ по информатике на 80 баллов",
        "Люблю программирование",
        "Мой любимый фильм - 'Как приручить дракона'",
        "Мечтаю поступить в КФУ"
    ];
    let factCount = 0;
    
    loadBtn.onclick = function() {
        if (factCount < facts.length) {
            const newDiv = document.createElement('div');
            newDiv.className = 'fact-item';
            newDiv.innerHTML = `<p>${facts[factCount]}</p>`;
            container.appendChild(newDiv);
            factCount++;
            
            if (factCount === facts.length) {
                loadBtn.style.display = 'none';
            }
        }
    }
}


const menu = document.getElementById('menu-trigger');
if (menu) {
    menu.onclick = function() {
        const m = document.getElementById('dropdown-content');
        m.classList.toggle('show');
    }
}


function initValidation() {
    const validators = {
        name: (value) => value && value.length >= 2,
        age: (value) => value && value >= 0 && value <= 130,
        email: (value) => value && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value),
        comment: (value) => value && value.trim().length > 0
    };
    
    const errorMessages = {
        name: 'Должно содержать минимум 2 символа',
        age: 'Возраст должен быть от 0 до 130',
        email: 'Введите корректный email',
        comment: 'Комментарий обязателен'
    };
    
    document.querySelectorAll('input, textarea').forEach(input => {
        input.addEventListener('blur', function() {
            const errorId = this.id + '-error';
            const errorElement = document.getElementById(errorId);
            
            if (errorElement && validators[this.id]) {
                const isValid = validators[this.id](this.value);
                
                if (!isValid) {
                    errorElement.textContent = errorMessages[this.id];
                    errorElement.style.display = 'block';
                    this.classList.add('error');
                } else {
                    errorElement.style.display = 'none';
                    this.classList.remove('error');
                }
            }
        });
        
        input.addEventListener('focus', function() {
            const errorId = this.id + '-error';
            const errorElement = document.getElementById(errorId);
            if (errorElement) {
                errorElement.style.display = 'none';
                this.classList.remove('error');
            }
        });
    });
    

    const form = document.getElementById('feedback-form');
    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            
            let isValid = true;
            const formData = {};
            
            document.querySelectorAll('input, textarea').forEach(input => {
                if (validators[input.id]) {
                    if (!validators[input.id](input.value)) {
                        isValid = false;
                        const errorElement = document.getElementById(input.id + '-error');
                        if (errorElement) {
                            errorElement.textContent = errorMessages[input.id];
                            errorElement.style.display = 'block';
                            input.classList.add('error');
                        }
                    }
                }
                formData[input.id] = input.value;
            });
            
            if (isValid) {
                const messageElement = document.getElementById('form-message');
                if (messageElement) {
                    messageElement.textContent = 'Форма успешно отправлена!';
                    messageElement.style.color = 'green';
                    messageElement.style.display = 'block';
                }
                console.log('Данные формы:', formData);
                form.reset();
            }
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    initSlider();
    initTimer();
    initValidation();
});
