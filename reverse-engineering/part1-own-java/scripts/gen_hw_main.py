"""Build the reconstructed hardware Main from the obfuscated decompile.

The data tables are copied mechanically (only the obfuscated type names change);
the control flow is rewritten by hand in TEMPLATE below.
"""
import re
import sys

src_path, out_path = sys.argv[1], sys.argv[2]
src = open(src_path, encoding='utf-8').read()

TYPES = {'a': 'GraphicsCard', 'c': 'Processor', 'd': 'GameInfo', 'b': 'GameBenchmark'}
tables = {name: [] for name in TYPES.values()}
for line in src.splitlines():
    m = re.search(r'\.put\((".*?"), new ([abcd])\((.*)\)\);\s*$', line)
    if m:
        key, typ, args = m.groups()
        tables[TYPES[typ]].append(f'        {{var}}.put({key}, new {TYPES[typ]}({args}));')

def block(name, var):
    return '\n'.join(row.replace('{var}', var) for row in tables[name])

TEMPLATE = '''import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Reconstructed from the obfuscated Main: asks for a GPU, a CPU and two games, then prints
// specs, a combined score, a bottleneck verdict, game requirements and an FPS estimate.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sisteminizi puanlamamız için , lütfen sırasısyla sistem parçalarınızı giriniz ");

        Map<String, GraphicsCard> gpus = new HashMap<>();
__GPUS__
        System.out.println("Sorgulamak istediğiniz ekran kartı modelini girin , (tüm harfler büyük) örnek : RTX 3060 , RTX 4060 , RX 6700 XT , RX 7900 XTX");
        String gpuKey;
        while (!gpus.containsKey(gpuKey = scanner.nextLine().strip().toUpperCase())) {
            System.out.println("Sistemde böyle bir ekran kartı yok , lütfen tekrar deneyiniz");
            System.out.println("Örnek : RTX 2080 , RTX 4090 , RX 6800 XT , RX 9700 XT");
            System.out.print("Tekrar deneyin > ");
        }
        GraphicsCard gpu = gpus.get(gpuKey);
        gpu.printSpecs();

        Map<String, Processor> cpus = new HashMap<>();
__CPUS__
        System.out.println("Sorgulamak istediğniz işlemci modelini girin  , (tüm harfler büyük) örnek : INTEL CORE i3-12100 , INTEL CORE i7-13700K , RYZEN 7 7700X , RYZEN 7 7800X3D ");
        String cpuKey;
        while (!cpus.containsKey(cpuKey = scanner.nextLine().strip().toUpperCase())) {
            System.out.println("Sistemde böyle bir işlemci yok , lütfen tekrar deneyiniz");
            System.out.println("Örnek : INTEL CORE i3-14100 , INTEL CORE i9-12900 , INTEL CORE i9-14900K , RYZEN 9 7950X , RYZEN 9 9950X3D ");
            System.out.print("Tekrar deneyin > ");
        }
        Processor cpu = cpus.get(cpuKey);
        cpu.printSpecs();

        System.out.println("Sisteminizin toplam performans puanı");
        SystemScore score = new SystemScore();
        System.out.println(score.averageScore(gpu, cpu));
        System.out.println("--------------------");
        System.out.println("Sisteminizin darboğaz hesabı\\n");
        score.printBottleneck(gpu, cpu);

        Map<String, GameInfo> games = new HashMap<>();
        System.out.println("\\n--------------------------------------------------------");
        System.out.println("Sistem gereksinimlerini ve oyun bilgilerini öğrenmek istediğiniz oyunu giriniz , (tüm harfler büyük) örnek : THE LAST OF US PART 2");
__GAMES__
        String gameKey;
        while (!games.containsKey(gameKey = scanner.nextLine().strip())) {
            System.out.println("Sistemde böyle oyun yok lütfen tekrar giriniz");
            System.out.println("Örnek : The Last Of Us Remake , Red Redemption 2 , Cities Skylines 2");
            System.out.print("Tekrar deneyin > ");
        }
        games.get(gameKey).printInfo();

        Map<String, GameBenchmark> benchmarks = new HashMap<>();
__BENCH__
        String benchmarkKey;
        while (true) {
            System.out.println("\\nLütfen sisteminiz için benchmark yapmak istediğiniz oyunu seçin");
            benchmarkKey = scanner.nextLine().strip();
            if (benchmarks.containsKey(benchmarkKey)) break;
            System.out.println("Sistemde böyle oyun yok lütfen tekrar giriniz");
            System.out.println("Örnek : The Last Of Us Remake , Red Redemption 2 , Cities Skylines 2");
            System.out.print("Tekrar deneyin > ");
        }
        benchmarks.get(benchmarkKey).printEstimate(gpu, cpu);
    }
}
'''

out = (TEMPLATE
       .replace('__GPUS__', block('GraphicsCard', 'gpus'))
       .replace('__CPUS__', block('Processor', 'cpus'))
       .replace('__GAMES__', block('GameInfo', 'games'))
       .replace('__BENCH__', block('GameBenchmark', 'benchmarks')))
open(out_path, 'w', encoding='utf-8').write(out)
print({k: len(v) for k, v in tables.items()})
