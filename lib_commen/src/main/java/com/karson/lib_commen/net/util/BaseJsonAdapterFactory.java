package com.karson.lib_commen.net.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * <p>标题: BaseJson的data数据为空字符串“”,报错处理</p>
 * <p>描述:  </p>
 * <p>版权: Copyright (c) 2017</p>
 * <p>公司: </p>
 *
 * @Anthor: Jeeson
 * @Time: 2017/8/24 15:47
 */
public class BaseJsonAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {

        Class<? super T> rawType = typeToken.getRawType();
        TypeAdapter<T> originAdapter = gson.getAdapter(typeToken);

        //如果是String类型，则直接用原来的Adapter转换
        return String.class.equals(rawType) ? originAdapter : new Adapter(gson,originAdapter, rawType);
    }

    private class Adapter<E> extends TypeAdapter<E> {

        private final Gson context;
        private final Class<E> componentType;
        private final TypeAdapter<E> componentTypeAdapter;

        public Adapter(Gson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
            this.context = context;
            this.componentTypeAdapter =componentTypeAdapter;
            this.componentType = componentType;
        }

        @Override
        public void write(JsonWriter out, E value) throws IOException {
            try {
                if(componentTypeAdapter != null) {
                    componentTypeAdapter.write(out, value);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public E read(final JsonReader in) throws IOException {

            try {
                //如果下一个为JsonToken.STRING的起点，说明是下个节点是字符串
                if (in.peek() == JsonToken.STRING) {
                    in.skipValue();
                    return null;
                }
                if(componentTypeAdapter != null) {
                    return componentTypeAdapter.read(in);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
}
