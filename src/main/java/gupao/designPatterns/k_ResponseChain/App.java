package gupao.designPatterns.k_ResponseChain;

public class App {
    public static void main(String[] args) {
        ProcessorChain chain=new ProcessorChain();
        PreProcessor preProcessor=new PreProcessor(chain);
        DoProcessor doProcessor=new DoProcessor(chain);
        PostProcessor postProcessor=new PostProcessor(chain);

        Request request=new Request();
        request.setMessage("123");

        chain.doChain(request,null);
        System.out.println(request.getMessage());
    }

}
