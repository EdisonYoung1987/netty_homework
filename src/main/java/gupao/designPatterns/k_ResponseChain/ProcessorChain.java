package gupao.designPatterns.k_ResponseChain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**处理链*/
public class ProcessorChain {
    private  List<IProcesser> list;

    public ProcessorChain() {
        this.list = new ArrayList<>(16);
    }

    public void registProcessor(IProcesser processer){
        list.add(processer);
    }

    public IProcesser getNextProcessor(IProcesser processer){
        int idx=list.indexOf(processer);
        if(idx==list.size()-1){
            return null;
        }else{
            return list.get(idx+1);
        }
    }

    public void doChain(Request request,Response response){
        if(list.size()==0){
            return;
        }else{
            list.get(0).doProcess(request,response);
        }
    }
}
