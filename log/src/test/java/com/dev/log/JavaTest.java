package com.dev.log;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaTest {
    
    @Test
    public void newTest(){

        int[] numbers = {5, 2, 8, 1, 9, 3, 6};
        Integer[] xx = Arrays.stream(numbers).boxed().toArray(Integer[]::new);
        Arrays.sort(xx, Collections.reverseOrder());
        int[] tmp = Arrays.stream(xx).mapToInt(i->i).toArray();
        int[] answer = Arrays.copyOfRange(tmp, 0, tmp.length - 5 );

        System.out.println("역순 정렬된 숫자 배열: " + Arrays.toString(answer));
    }
}
