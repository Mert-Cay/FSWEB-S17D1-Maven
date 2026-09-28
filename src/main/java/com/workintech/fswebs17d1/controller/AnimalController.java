package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/animal") // Opsiyonel ama best practice (iyi pratik)
public class AnimalController {
    private Map<Integer, Animal> animals = new HashMap<Integer, Animal>();

    @GetMapping
    public List<Animal> getAllAnimals() {
        // Map'in sadece 'value' kısımlarını (Animal nesnelerini) alıp yeni bir List'e dönüştürüyoruz.
        return new ArrayList<>(animals.values());
    }

    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable Integer id) {
        // Eğer o ID map'te varsa hayvanı döndürür, yoksa null döner.
        return animals.get(id);
    }

    @PostMapping
    public Animal addAnimal(@RequestBody Animal newAnimal) {
        // Gelen 'newAnimal' nesnesinin id değerini key (anahtar), kendisini ise value (değer) olarak map'e ekliyoruz.
        animals.put(newAnimal.getId(), newAnimal);
        return newAnimal; // Eklenen nesneyi de geri döndürerek işlemin başarılı olduğunu gösteriyoruz.
    }

    @PutMapping("/{id}")
    public Animal updateAnimal(@PathVariable Integer id, @RequestBody Animal updatedAnimal) {
        // Önce belirtilen ID'de bir hayvan var mı diye kontrol edebiliriz (opsiyonel ama sağlıklı bir adım)
        if(animals.containsKey(id)) {
            // Map'teki put metodu, aynı ID (key) ile yeni bir değer eklendiğinde eski değeri üzerine yazar (günceller)
            updatedAnimal.setId(id); // Body'den gelen objenin id'sini path'den gelenle zorunlu eşitleyelim (güvenlik için)
            animals.put(id, updatedAnimal);
            return updatedAnimal;
        }
        return null; // ID bulunamadıysa (İleride burada hata fırlatmayı öğreneceksin)
    }

    @DeleteMapping("/{id}")
    public Animal deleteAnimal(@PathVariable Integer id) {
        // map.remove(key) metodu, kaydı siler ve eğer kayıt vardıysa sildiği değeri geri döndürür.
        return animals.remove(id);
    }
}
