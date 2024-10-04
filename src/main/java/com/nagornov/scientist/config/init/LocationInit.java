package com.nagornov.scientist.config.init;

import com.nagornov.scientist.model.Location;
import com.nagornov.scientist.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LocationInit implements ApplicationRunner {

    private final LocationRepository locationRepository;

    @Override
    public void run(ApplicationArguments args) {
        addLocationIfNotExists(new Location(1, "Россия", "Алтайский край"));
        addLocationIfNotExists(new Location(2, "Россия", "Амурская область"));
        addLocationIfNotExists(new Location(3, "Россия", "Архангельская область"));
        addLocationIfNotExists(new Location(4, "Россия", "Астраханская область"));
        addLocationIfNotExists(new Location(5, "Россия", "Белгородская область"));
        addLocationIfNotExists(new Location(6, "Россия", "Брянская область"));
        addLocationIfNotExists(new Location(7, "Россия", "Владимирская область"));
        addLocationIfNotExists(new Location(8, "Россия", "Волгоградская область"));
        addLocationIfNotExists(new Location(9, "Россия", "Вологодская область"));
        addLocationIfNotExists(new Location(10, "Россия", "Воронежская область"));
        addLocationIfNotExists(new Location(11, "Россия", "Еврейская автономная область"));
        addLocationIfNotExists(new Location(12, "Россия", "Забайкальский край"));
        addLocationIfNotExists(new Location(13, "Россия", "Ивановская область"));
        addLocationIfNotExists(new Location(14, "Россия", "Иркутская область"));
        addLocationIfNotExists(new Location(15, "Россия", "Кабардино-Балкарская Республика"));
        addLocationIfNotExists(new Location(16, "Россия", "Калининградская область"));
        addLocationIfNotExists(new Location(17, "Россия", "Калужская область"));
        addLocationIfNotExists(new Location(18, "Россия", "Камчатский край"));
        addLocationIfNotExists(new Location(19, "Россия", "Карачаево-Черкесская Республика"));
        addLocationIfNotExists(new Location(20, "Россия", "Кемеровская область"));
        addLocationIfNotExists(new Location(21, "Россия", "Кировская область"));
        addLocationIfNotExists(new Location(22, "Россия", "Костромская область"));
        addLocationIfNotExists(new Location(23, "Россия", "Краснодарский край"));
        addLocationIfNotExists(new Location(24, "Россия", "Красноярский край"));
        addLocationIfNotExists(new Location(25, "Россия", "Курганская область"));
        addLocationIfNotExists(new Location(26, "Россия", "Курская область"));
        addLocationIfNotExists(new Location(27, "Россия", "Ленинградская область"));
        addLocationIfNotExists(new Location(28, "Россия", "Липецкая область"));
        addLocationIfNotExists(new Location(29, "Россия", "Магаданская область"));
        addLocationIfNotExists(new Location(30, "Россия", "Москва"));
        addLocationIfNotExists(new Location(31, "Россия", "Московская область"));
        addLocationIfNotExists(new Location(32, "Россия", "Мурманская область"));
        addLocationIfNotExists(new Location(33, "Россия", "Нижегородская область"));
        addLocationIfNotExists(new Location(34, "Россия", "Новгородская область"));
        addLocationIfNotExists(new Location(35, "Россия", "Новосибирская область"));
        addLocationIfNotExists(new Location(36, "Россия", "Омская область"));
        addLocationIfNotExists(new Location(37, "Россия", "Оренбургская область"));
        addLocationIfNotExists(new Location(38, "Россия", "Орловская область"));
        addLocationIfNotExists(new Location(39, "Россия", "Пензенская область"));
        addLocationIfNotExists(new Location(40, "Россия", "Пермский край"));
        addLocationIfNotExists(new Location(41, "Россия", "Приморский край"));
        addLocationIfNotExists(new Location(42, "Россия", "Псковская область"));
        addLocationIfNotExists(new Location(43, "Россия", "Республика Адыгея"));
        addLocationIfNotExists(new Location(44, "Россия", "Республика Алтай"));
        addLocationIfNotExists(new Location(45, "Россия", "Республика Башкортостан"));
        addLocationIfNotExists(new Location(46, "Россия", "Республика Бурятия"));
        addLocationIfNotExists(new Location(47, "Россия", "Республика Дагестан"));
        addLocationIfNotExists(new Location(48, "Россия", "Республика Ингушетия"));
        addLocationIfNotExists(new Location(49, "Россия", "Республика Калмыкия"));
        addLocationIfNotExists(new Location(50, "Россия", "Республика Карелия"));
        addLocationIfNotExists(new Location(51, "Россия", "Республика Коми"));
        addLocationIfNotExists(new Location(52, "Россия", "Республика Крым"));
        addLocationIfNotExists(new Location(53, "Россия", "Республика Марий Эл"));
        addLocationIfNotExists(new Location(54, "Россия", "Республика Мордовия"));
        addLocationIfNotExists(new Location(55, "Россия", "Республика Саха (Якутия)"));
        addLocationIfNotExists(new Location(56, "Россия", "Республика Северная Осетия-Алания"));
        addLocationIfNotExists(new Location(57, "Россия", "Республика Татарстан"));
        addLocationIfNotExists(new Location(58, "Россия", "Республика Тыва"));
        addLocationIfNotExists(new Location(59, "Россия", "Республика Хакасия"));
        addLocationIfNotExists(new Location(60, "Россия", "Ростовская область"));
        addLocationIfNotExists(new Location(61, "Россия", "Рязанская область"));
        addLocationIfNotExists(new Location(62, "Россия", "Самарская область"));
        addLocationIfNotExists(new Location(63, "Россия", "Санкт-Петербург"));
        addLocationIfNotExists(new Location(64, "Россия", "Саратовская область"));
        addLocationIfNotExists(new Location(65, "Россия", "Сахалинская область"));
        addLocationIfNotExists(new Location(66, "Россия", "Свердловская область"));
        addLocationIfNotExists(new Location(67, "Россия", "Севастополь"));
        addLocationIfNotExists(new Location(68, "Россия", "Смоленская область"));
        addLocationIfNotExists(new Location(69, "Россия", "Ставропольский край"));
        addLocationIfNotExists(new Location(70, "Россия", "Тамбовская область"));
        addLocationIfNotExists(new Location(71, "Россия", "Тверская область"));
        addLocationIfNotExists(new Location(72, "Россия", "Томская область"));
        addLocationIfNotExists(new Location(73, "Россия", "Тульская область"));
        addLocationIfNotExists(new Location(74, "Россия", "Тюменская область"));
        addLocationIfNotExists(new Location(75, "Россия", "Удмуртская Республика"));
        addLocationIfNotExists(new Location(76, "Россия", "Ульяновская область"));
        addLocationIfNotExists(new Location(77, "Россия", "Хабаровский край"));
        addLocationIfNotExists(new Location(78, "Россия", "Ханты-Мансийский автономный округ - Югра"));
        addLocationIfNotExists(new Location(79, "Россия", "Челябинская область"));
        addLocationIfNotExists(new Location(80, "Россия", "Чеченская Республика"));
        addLocationIfNotExists(new Location(81, "Россия", "Чувашская Республика"));
        addLocationIfNotExists(new Location(82, "Россия", "Ямало-Ненецкий автономный округ"));
        addLocationIfNotExists(new Location(83, "Россия", "Ярославская область"));

        addLocationIfNotExists(new Location(84, "Абхазия", null));
        addLocationIfNotExists(new Location(85, "Азербайджан", null));
        addLocationIfNotExists(new Location(86, "Антигуа и Барбуда", null));
        addLocationIfNotExists(new Location(87, "Армения", null));
        addLocationIfNotExists(new Location(88, "Беларусь", null));
        addLocationIfNotExists(new Location(89, "Болгария", null));
        addLocationIfNotExists(new Location(90, "Греция", null));
        addLocationIfNotExists(new Location(91, "Грузия", null));
        addLocationIfNotExists(new Location(92, "Донецкая Народная Республика", null));
        addLocationIfNotExists(new Location(93, "Египет", null));
        addLocationIfNotExists(new Location(94, "Израиль", null));
        addLocationIfNotExists(new Location(95, "Испания", null));
        addLocationIfNotExists(new Location(96, "Казахстан", null));
        addLocationIfNotExists(new Location(97, "Китай", null));
        addLocationIfNotExists(new Location(98, "Кыргызстан", null));
        addLocationIfNotExists(new Location(99, "Луганская Народная Республика", null));
        addLocationIfNotExists(new Location(100, "Молдова", null));
        addLocationIfNotExists(new Location(101, "Мьянма", null));
        addLocationIfNotExists(new Location(102, "Нидерланды", null));
        addLocationIfNotExists(new Location(103, "Объединенные Арабские Эмираты", null));
        addLocationIfNotExists(new Location(104, "Польша", null));
        addLocationIfNotExists(new Location(105, "Сербия", null));
        addLocationIfNotExists(new Location(106, "Сирийская Арабская Республика", null));
        addLocationIfNotExists(new Location(107, "США", null));
        addLocationIfNotExists(new Location(108, "Таджикистан", null));
        addLocationIfNotExists(new Location(109, "Туркменистан", null));
        addLocationIfNotExists(new Location(110, "Узбекистан", null));
        addLocationIfNotExists(new Location(111, "Украина", null));
        addLocationIfNotExists(new Location(112, "Франция", null));
        addLocationIfNotExists(new Location(113, "Хорватия", null));
        addLocationIfNotExists(new Location(114, "Черногория", null));
        addLocationIfNotExists(new Location(115, "Эстония", null));
        addLocationIfNotExists(new Location(116, "Южный Судан", null));
    }

    private void addLocationIfNotExists(Location location) {
        locationRepository.findLocationByCountryAndRegionIsNull(location.getCountry(), location.getRegion())
                .orElseGet(() -> locationRepository.save(location));
    }

}
