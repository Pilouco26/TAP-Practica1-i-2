/*package examen;

public class examen {
    interface A { public void a(); }
    interface B { public void b(); }
    interface C { public void c(); }

    class D implements A, B {
        public void a() { System.out.println("BBAA"); }
        public void b() { System.out.println("BBBB"); }
        public void d() { System.out.println("DDEE"); }
    }

    class E extends D implements C {
        public void c() { System.out.println("CCC"); }
        public void e() { System.out.println("EEEE"); }
        public void a() { System.out.println("EEAA"); }
    }

    public static void main(String[] args) {
        A a = new E();
        C c = new E();
        B b = c;
        c.c();
        c.e();
        E e = new E();
        e.b();
        Object list[] = new D[3];
        list[0] = e;
        list[1] =  a;
        list[2] =  c;
        for (D elem : list)
            (D)elem.d();
    }
}
*/