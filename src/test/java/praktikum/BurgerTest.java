package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private Burger burger;
    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockSauce;
    @Mock
    private Ingredient mockFilling;
    @Before
    public void setUp(){
        burger = new Burger();
    }
    //сохранение булочки
    @Test
    public void setBunsSavedBunTest() {
        burger.setBuns(mockBun);
        assertEquals("Булочки нет", mockBun, burger.bun);
    }
    //добавление ингредиента
    @Test
    public void addIngredientAddedSauceSuccessfullyTest(){
        burger.addIngredient(mockSauce);
        assertTrue("Ингредиента нет", burger.ingredients.contains(mockSauce));
    }
    //удаление ингредиента
    @Test
    public void removeIngredientDeletedByIndexSuccessfullyTest(){
        burger.addIngredient(mockSauce);
        burger.removeIngredient(0);
        assertTrue("Ингредиент не удалился", burger.ingredients.isEmpty());
    }
    //перемещение ингредиента
    @Test
    public void moveIngredientIndicesExchangedSuccessfullyTest() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        burger.moveIngredient(0, 1);
        assertEquals("Начинка осталась под индексом 1",
                mockFilling, burger.ingredients.get(0));
    }
    //вывод чека
    @Test
    public void getReceiptValidBurgerReturnsFormattedStringTest() {
        //Обучаем моки возвращать нужные строки и цены
        Mockito.when(mockBun.getName()).thenReturn("black bun");
        Mockito.when(mockBun.getPrice()).thenReturn(100.0f);

        Mockito.when(mockSauce.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(mockSauce.getName()).thenReturn("hot sauce");
        Mockito.when(mockSauce.getPrice()).thenReturn(50.0f);

        //Собираем бургер
        burger.setBuns(mockBun);
        burger.addIngredient(mockSauce);

        //Получаем чек бургера
        String receipt = burger.getReceipt();

        //Проверка
        assertTrue("Чек должен содержать имя булочки",
                receipt.contains("(==== black bun ====)"));
        assertTrue("Чек должен содержать тип и имя ингредиента",
                receipt.contains("= sauce hot sauce ="));
        assertTrue("Чек должен содержать правильную итоговую цену",
                receipt.contains("Price: 250"));
    }

}
