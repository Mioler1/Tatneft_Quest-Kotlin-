package com.example.tatneft_quest.travelPackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.LIST_DATA_POINTS
import com.example.tatneft_quest.Variables.Companion.LIST_DATA_TEST
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.Variables.Companion.testSheet
import com.example.tatneft_quest.databinding.FragmentQuestBinding
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.libs.ImprovedPreference
import com.example.tatneft_quest.models.ClusterMarkerPoints
import com.example.tatneft_quest.models.TestQuestionsModel
import kotlin.collections.ArrayList

class QuestFragment : BaseFragment() {
    private lateinit var improvedPreference: ImprovedPreference
    private lateinit var listPoint: ArrayList<ClusterMarkerPoints>
    private lateinit var listTest: ArrayList<TestQuestionsModel>
    private lateinit var binding: FragmentQuestBinding

    private lateinit var shamsinur: String
    private lateinit var healthPark: String
    private lateinit var cascadeOfProud: String
    private lateinit var cityPark: String

    private lateinit var shamsinurOne: String
    private lateinit var shamsinurTwo: String
    private lateinit var shamsinurThree: String
    private lateinit var healthParkOne: String
    private lateinit var healthParkTwo: String
    private lateinit var healthParkThree: String
    private lateinit var cascadeOfProudOne: String
    private lateinit var cascadeOfProudTwo: String
    private lateinit var cascadeOfProudThree: String
    private lateinit var cityParkOne: String
    private lateinit var cityParkTwo: String
    private lateinit var cityParkThree: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        binding.city.setOnClickListener {
            putData()
            mFragmentHandler?.replace(StartGeneralFragment(), true)
        }
    }

    private fun putData() {
        if (listPoint.isNotEmpty()) {
            pointsSheet = listPoint
        } else {
            fillingSheetForPoints(1, 54.903642, 52.281305,
                "Парк Шамсинур", R.drawable.icon1, true, shamsinur)
            fillingSheetForPoints(2, 54.897804, 52.266678,
                "Парк здоровья", R.drawable.icon2, false, healthPark)
            fillingSheetForPoints(3, 54.904369, 52.287813,
                "Каскад прудов", R.drawable.icon3, false, cascadeOfProud)
            fillingSheetForPoints(4, 54.898794, 52.29991,
                "Городской парк", R.drawable.icon4, false, cityPark)
            improvedPreference.putListObjectMarker(LIST_DATA_POINTS, pointsSheet)
        }
        if (listPoint.isNotEmpty()) {
            testSheet = listTest
        } else {
            fillingSheetForTest(1, 1, shamsinurOne,
                "2", "4",
                "1", "1")
            fillingSheetForTest(1, 2, shamsinurTwo,
                "7", "6",
                "5", "6")
            fillingSheetForTest(1, 3, shamsinurThree,
                "В амфитеатре", "В драмтеатре",
                "В ДК Нефтьче", "В амфитеатре")
            fillingSheetForTest(2, 1, healthParkOne,
                "2 сентября 2018", "31 августа 2018",
                "7 сентября 2018", "2 сентября 2018")
            fillingSheetForTest(2,
                2,
                healthParkTwo,
                "Площадь с фонтаном, планетарий, детская площадка",
                "Детская, спортивная площадка, планетарий",
                "Детская, спортивная площдка, площадь с фонтаном",
                "Детская, спортивная площдка, площадь с фонтаном")
            fillingSheetForTest(2, 3, healthParkThree,
                "Древисина", "Металл",
                "Пластик", "Древисина")
            fillingSheetForTest(3, 1, cascadeOfProudOne,
                "4000м2", "5000м2",
                "4500м2", "5000м2")
            fillingSheetForTest(3, 2, cascadeOfProudTwo,
                "Земля, воздух, вода", "Земля, вода, огонь",
                "Огонь, вода, воздух", "Земля, воздух, вода")
            fillingSheetForTest(3, 3, cascadeOfProudThree,
                "2 сентября 2017 года", "6 сентября 2017 года",
                "3 сентября 2017 года", "3 сентября 2017 года")
            fillingSheetForTest(4, 1, cityParkOne,
                "2003", "2002",
                "2004", "2003")
            fillingSheetForTest(4, 2, cityParkTwo,
                "Ямашнефть", "Альметьевнефть ",
                "Елховнефть", "Альметьевнефть ")
            fillingSheetForTest(4, 3, cityParkThree,
                "Лучший парк республики", "Поющий фонтан",
                "Хрустальное колесо", "Хрустальное колесо")
            improvedPreference.putListObjectTest(LIST_DATA_TEST, testSheet)
        }
    }

    private fun init() {
        improvedPreference = ImprovedPreference(context)
        listPoint =
            improvedPreference.getListObjectMarker(LIST_DATA_POINTS,
                ClusterMarkerPoints::class.java)
        listTest =
            improvedPreference.getListObjectTest(LIST_DATA_TEST, TestQuestionsModel::class.java)

        shamsinur =
            "«Шамсинур» - это первая из 4-х новых парковых зон в городе Альметьевск, обустроенная в рамках Года парков и скверов. На реализацию проекта было выделено 76 млн. рублей. Две трети из этих средств – из республиканского бюджета, треть – из муниципального. В парковом организме чётко выделяется несколько «групп мышц»: настил для йоги, две игровые зоны, амфитеатр с деревянными сиденьями, дендрарий, спортивная площадка с тренажёрами и турниками, навес со столами для тихого отдыха и пикников (здесь ловит бесплатный Wi-Fi). Беговая и пешеходная дорожки служат главными «кровеносными сосудами парка». Парк оснащен площадкой для воркаута, для самых юных посетителей построены современные детские игровые площадки. \n" + "Торжественное открытие парка состоялось в амфитеатре. С приветственной речью выступил Айрат Хайруллин."
        healthPark =
            "2 сентября 2018 года Президент Республики Татарстан Рустам Минниханов, глава Альметьевского муниципального района Айрат Хайруллин и Генеральный директор ПАО «Татнефть» Наиль Маганов открыли в Альметьевске новый благоустроенный парк «Здоровье». Из-за близости парка к Детской и Центральной районной больницам, его сделали зоной тихого отдыха. Пациенты смогут гулять в парке, а время, проведенное здесь с родственниками и друзьями, позволит им быстрее идти на поправку. В функциональном наполнении парка выделяется три участка: площадь с фонтаном, детская площадка и оздоровительная спортивная площадка. Центральная площадь с фонтаном — знаковое место парка. Для отдыха и игры детей установили детскую площадку с батутами, горками и качелями. Основные её элементы изготовлены из натурального материала — дерева. В спортивной зоне появились тренажеры для реабилитации пациентов больницы и для людей с ограниченными возможностями здоровья. У входа в парк со стороны улицы Зифы Балакиной установили арт-объект — объемную надпись «Здоровье». На территории парка появился купольный детский развлекательный центр с планетарием. Современное интерактивное оборудование позволит детям узнать об устройстве планет и истории созвездий – изучить Солнечную систему и «увидеть» Млечный Путь."
        cascadeOfProud =
            "Парк «Каскад прудов» города Альметьевск – самая большая детская площадка в Республике Татарстан, площадью более 5000м2. Проект детской площадки был создан совместно с «Архидесант». Открытие парка «Каскад прудов» состоялось 3 сентября 2017 года. По задумке парк поделен на зоны, каждая из которых ассоциируется с одной из стихий: земля, воздух,вода. В зоне стихии «Земля» расположен большой многофункциональный паровоз, предназначенный для детей 4–15 лет. На фасаде имеются специальные ступеньки для лазания, и двойная горка сзади. Так же в этой зоне мы расположили Трактор, Самолет и Бульдозер. Комплекс «Замок». Также в этой зоне расположена песочница в виде утонувшего корабля, она рассчитана для детей с ограниченными возможностями, в том числе на инвалидном кресле. Зона стихии «Воздух» оборудована батутами и многочисленными качелями. С другого конца игровой зоны установлены различные музыкальные оборудования. Зону водной стихии создает голубое резиновое покрытие, которое эмитирует реку и является своеобразными дорожками – связующими всего парка."
        cityPark =
            "29 августа 2001 года произошло торжественное открытие городского парка культуры и отдыха г. Альметьевска. Гостям и жителям города был представлен парк европейского уровня, аналогов которому не было в республике Татарстан. В 2003 году в честь юбилея Постановлением Главы администрации наш парк переименован в «Городской парк имени 60-летия нефти Татарстана». Для обеспечения соответствия парка этому высокому имени, парк был закреплен за НГДУ «Альметьевнефть». \n" + "Посетители городского парка могут пользоваться услугами 25 аттракционов, спортивными площадками, теннисными кортами, беговой дорожкой, торговыми рядами, детским кафе «Ассоль» и другими атрибутами культурного досуга. На территории парка ежегодно высаживаются более 25 видов однолетних и многолетних цветов на 23 клумбах, площадью 0,4 га. В 2007 году на территории парка был открыт детский зоопарк, где содержатся миниатюрная лошадь, пони, косули, дикобразы, белки, кролики, страусы, павлины, фазаны, куры, попугаи, орлы. В результате совместного труда нефтяников и самого коллектива фонда наш парк стал трехкратным победителем на ежегодном смотре-конкурсе «Хрустальное колесо» среди парков России и стран СНГ. Парк продолжает свое обновление благодаря поддержке оказываемой компанией «Татнефть» и в юбилейном году здесь был построен новый светомузыкальный фонтан, который представляет собой круглую чашу с бортами облицованными плитами, изготовленными по технологии «жидкий камень», накрывные плиты бортов отделаны натуральным гранитом красного цвета."

        shamsinurOne =
            "Парк Шамсинур это какая по счету парковая зона в Альметьевске, обустроенная в рамках Года парков и скверов?"
        shamsinurTwo = "Сколько выделяют \"групп мышц\" в парке Шамсинур?"
        shamsinurThree = "Где состоялось торжественное открытие парка?"
        healthParkOne = "В какой день произошло торжественное открытие парка \"Здоровья\"?"
        healthParkTwo = "Какие 3 участка выделяют в функциональном наполнении парка?"
        healthParkThree = "Из какого материала изготовлены основные элементы парка?"
        cascadeOfProudOne =
            "Какова площадь самой большой детской площадки в Республике Татарстан?"
        cascadeOfProudTwo =
            "Парк здоровья поделен на зоны. С какими стихиями ассоциируются эти зоны?"
        cascadeOfProudThree = "Когда состоялось открытие парка \"Каскад прудов\"?"
        cityParkOne = "В каком году произошло переименование парка в \"Городской парк\"?"
        cityParkTwo =
            "Мы уже знаем по предыдущим локациям какие НГДУ входят в состав \"Татнефть\". За каким НГДУ было закреплено обеспечение соответсвия парка?"
        cityParkThree =
            "Как называется номинация, в который \"Городской парк\" ставится трехкратным победителем?"
    }
}