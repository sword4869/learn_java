// 运行不了----- [FAILED] Testing @Contended works on all results and infra objects. 好像是 II_Result 太老了
// 不会改!!!!!!!!!!!!!!!
// 而且我们期待是普通的代码形式, 日常用到的形式



// package thread_basic.volatile_test;
//
// import org.openjdk.jcstress.annotations.*;
// import org.openjdk.jcstress.infra.results.II_Result;
//
// @JCStressTest
// @Outcome(id = {"0, 0", "1, 1", "0, 1"}, expect = Expect.ACCEPTABLE, desc = "ACCEPTABLE")
// @Outcome(id = "1, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "INTERESTING")
// @State
// public class Reorder {
//
//     volatile int x;
//     int y;
//
//     @Actor
//     public void actor1() {
//         x = 1;
//         y = 1;
//     }
//
//     @Actor
//     public void actor2(II_Result r) {
//         r.r1 = y;
//         r.r2 = x;
//     }
// }
