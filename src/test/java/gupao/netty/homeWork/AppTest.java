package gupao.netty.homeWork;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String[] str="abc|234|22".split("\\|");
        for(String s:str){
        	System.out.println(s);
        }
    }
}
