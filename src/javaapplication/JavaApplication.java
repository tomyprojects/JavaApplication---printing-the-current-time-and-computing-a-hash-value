package javaapplication;

import java.util.Date;
import java.lang.Long;

interface infix_operator {

    public int apply(int x, int y);
}

class f implements infix_operator {

    public int apply(int x, int y) {
        return x + (2 * y);
    }
}

class g implements infix_operator {

    public int apply(int x, int y) {
        return (2*x) + y;
    }
}

class h implements infix_operator {

    public int apply(int x, int y) {
        return x + y;
    }
}

class k implements infix_operator {

    public int apply(int x, int y) {
        return x * y;
    }
}

public class JavaApplication {

    static long to_64bit_signed_integer (long value)
    {
        long bit16 = value & 65535;
        value = (bit16 << 16) | bit16;
        value = (value << 16) | bit16;
        value = (value << 16) | bit16;
        return Long.reverse(value);
    }

    public static void main(String[] args) {
        long current_time_value = new Date().getTime();
        System.out.println("Current Time: " + current_time_value);

        infix_operator[] operator_array = {new f(), new g(), new h(), new k()};

        int [] node = new int[31];
        int [] to_signed_value = new int[] {0,-2,1,-1};
        int [] to_unsigned_value = new int[] {0,2,1,3};

        long value = to_64bit_signed_integer (current_time_value);

        value = value >> 2;

        for (int leaf_index=30; leaf_index>=15; leaf_index--)
        {
            node[leaf_index] = to_signed_value [(int)(value & 3)];
            value = value >> 2;
        }

        for (int internal_index = 14; internal_index >= 0; internal_index--)
        {
            int op = to_unsigned_value [(int)(value & 3)];
            int child_index = 2*internal_index + 1;
            node [internal_index] = operator_array[op].apply(node[child_index], node[child_index+1]);
            value = value >> 2;
        }

        System.out.println("Hash Value: " + node[0]);

    }
}
