package net.study.tasks.benchmark;

import net.study.tasks.infrastructure.ApplicationEntryPoint;
import net.study.tasks.model.Component1;
import net.study.tasks.model.Component2;
import net.study.tasks.model.Component3;
import net.study.tasks.model.Component4;
import net.study.tasks.model.Component5;
import net.study.tasks.model.EntryPoint;
import net.study.tasks.model.LazyComponent1;
import net.study.tasks.model.LazyComponent2;
import net.study.tasks.model.LazyComponent3;
import net.study.tasks.model.LazyComponent4;
import net.study.tasks.model.LazyComponent5;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Random;

@Warmup(iterations = 2)
@Measurement(iterations = 2)
public class DIBenchmark {

    @State(Scope.Thread)
    public static class BenchState {
        public int a;

        @Setup
        public void setup() {
            Random r = new Random();
            a = r.nextInt();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(warmups = 1)
    public int testWithDependencyInjection(BenchState state) {
        ApplicationEntryPoint.launch(EntryPoint.class);
        return state.a;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int testWithoutDependencyInjection(BenchState state) {
        LazyComponent1 lazyComponent1 = new LazyComponent1();
        LazyComponent2 lazyComponent2 = new LazyComponent2();
        LazyComponent3 lazyComponent3 = new LazyComponent3();
        LazyComponent5 lazyComponent5 = new LazyComponent5();
        lazyComponent1.setLazyComponent5(lazyComponent5);
        LazyComponent4 lazyComponent4 = new LazyComponent4(lazyComponent5);
        Component5 component5 = new Component5(lazyComponent2, lazyComponent3);
        Component4 component4 = new Component4(component5);
        Component3 component3 = new Component3();
        component3.setLazyComponent4(lazyComponent4);
        component3.setLazyComponent5(lazyComponent5);
        Component2 component2 = new Component2();
        component2.setComponent4(component4);
        component2.setComponent5(component5);
        component2.setLazyComponent3(lazyComponent3);
        Component1 component1 = new Component1(component2, component3, lazyComponent1, lazyComponent2);
        EntryPoint entryPoint = new EntryPoint();
        entryPoint.setComponent1(component1);
        entryPoint.run();
        return state.a;
    }
}
